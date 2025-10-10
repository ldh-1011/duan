import { Client, StompSubscription, IMessage, Frame } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { localStorageAction } from "@/utils/storage";
import { ACCESS_TOKEN_STORAGE_KEY } from "@/constants/storagekey";

export const { VITE_BASE_URL_CLIENT_SOCKET } = import.meta.env || {};

type SubscriptionItem = {
    stompSub: StompSubscription;
    callbacks: Set<(msg: any) => void>;
};

class WebSocketService {
    private client: Client | null = null;
    private connected = false;
    private subscriptions: Record<string, SubscriptionItem> = {};
    private isConnecting = false;
    private waitingCallbacks: (() => void)[] = [];
    private pendingSubscriptions: Record<string, Set<(msg: any) => void>> = {};

    connect(callback?: () => void) {
        if (this.connected) {
            console.log("✅ WebSocket đã kết nối.");
            callback?.();
            return;
        }

        if (this.isConnecting) {
            if (callback) this.waitingCallbacks.push(callback);
            return;
        }

        this.isConnecting = true;

        const token = localStorageAction.get(ACCESS_TOKEN_STORAGE_KEY);
        if (!token) {
            console.warn("🚨 Token chưa có trong localStorage!");
        }

        const socket = new SockJS(`${VITE_BASE_URL_CLIENT_SOCKET}/ws`);

        this.client = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
            connectHeaders: {
                Authorization: `Bearer ${token || ""}`,
            },

            onConnect: (frame: Frame) => {
                console.log("✅ WebSocket đã kết nối:", frame);
                this.connected = true;
                this.isConnecting = false;

                callback?.();
                this.waitingCallbacks.forEach((cb) => cb());
                this.waitingCallbacks = [];

                // Đăng ký lại các pendingSubscriptions
                Object.entries(this.pendingSubscriptions).forEach(([destination, callbacks]) => {
                    callbacks.forEach((cb) => this.subscribe(destination, cb));
                });
                this.pendingSubscriptions = {};
            },

            onStompError: (frame) => {
                console.error("❌ STOMP Error:", frame);
            },

            onWebSocketClose: () => {
                console.warn("⚠️ WebSocket đóng kết nối. Thử reconnect sau 5s...");
                this.connected = false;
                this.isConnecting = false;

                setTimeout(() => this.connect(), 5000);
            },
        });

        this.client.activate();
    }

    subscribe(destination: string, callback: (msg: any) => void) {
        if (!this.client || !this.connected) {
            console.warn(`⏳ Chưa kết nối, delay subscribe topic: ${destination}`);
            if (!this.pendingSubscriptions[destination]) {
                this.pendingSubscriptions[destination] = new Set();
            }
            this.pendingSubscriptions[destination].add(callback);
            this.connect(() => this.subscribe(destination, callback));
            return;
        }

        if (!this.subscriptions[destination]) {
            const stompSub = this.client.subscribe(destination, (message: IMessage) => {
                let parsed;
                try {
                    parsed = JSON.parse(message.body);
                } catch {
                    parsed = message.body;
                }
                this.subscriptions[destination]?.callbacks.forEach((cb) => cb(parsed));
            });

            this.subscriptions[destination] = {
                stompSub,
                callbacks: new Set([callback]),
            };
        } else {
            this.subscriptions[destination].callbacks.add(callback);
        }
    }

    unsubscribe(destination: string) {
        const sub = this.subscriptions[destination];
        if (sub) {
            sub.stompSub.unsubscribe();
            delete this.subscriptions[destination];
            console.log(`🔕 Unsubscribed topic: ${destination}`);
        }
    }

    sendMessage(destination: string, message: any) {
        if (!this.client || !this.connected) {
            console.warn(`🚨 Chưa kết nối, delay gửi message tới: ${destination}`);
            this.connect(() => this.sendMessage(destination, message));
            return;
        }

        this.client.publish({
            destination,
            body: JSON.stringify(message),
        });
    }

    disconnect() {
        if (this.client) {
            Object.keys(this.subscriptions).forEach((destination) => this.unsubscribe(destination));
            this.client.deactivate();
            this.connected = false;
            this.isConnecting = false;
            console.log("🔌 Ngắt kết nối WebSocket hoàn toàn.");
        }
    }

    isConnected() {
        return this.connected;
    }
}

export const websocketService = new WebSocketService();

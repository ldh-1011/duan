import { createApp } from 'vue';
import { router } from '@/router/routes';
import { createPinia } from 'pinia';
import Antd from 'ant-design-vue';
import { VueQueryPlugin } from '@tanstack/vue-query';
import VueApexCharts from 'vue3-apexcharts';

import App from '@/App.vue';
import websocketPlugin from "@/services/configsocket/websocketPlugin";

import './index.scss';
import 'ant-design-vue/dist/reset.css';
import 'vue3-toastify/dist/index.css';
import './assets/style/style.css';
import '@fortawesome/fontawesome-free/css/all.min.css';

(window as any).global = window;

const app = createApp(App);

// Register plugin + libraries
app.use(createPinia());
app.use(router);
app.use(Antd);
app.use(VueQueryPlugin);
app.use(websocketPlugin);
app.component('apexchart', VueApexCharts); // ✅ chỉ register 1 lần

app.mount('#app');

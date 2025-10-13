package udpm.hn.server.test.infrastructure.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${frontend.url}")
    public String ALLOWED_ORIGIN;

    @Value("${ws.applicationPrefix}")
    private String applicationPrefix;

    // FIX: Đổi từ ws.topicPrefix thành ws.brokerPrefix
    @Value("${ws.brokerPrefix}")
    private String brokerPrefix;

    @Value("${ws.registerEndpoint}")
    private String registerEndpoint;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(brokerPrefix);
        registry.setApplicationDestinationPrefixes(applicationPrefix);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(registerEndpoint)
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins(ALLOWED_ORIGIN)
                .withSockJS();
    }

}

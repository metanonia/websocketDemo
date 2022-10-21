package com.metanonia.websocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * STOMP Configuration
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
        // subscribe message endpoint
        registry.enableSimpleBroker("/sub");
        // publishing message endpoint
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
        registry
                .addEndpoint("/ws-stomp")
                .setAllowedOrigins("*");
    }
}

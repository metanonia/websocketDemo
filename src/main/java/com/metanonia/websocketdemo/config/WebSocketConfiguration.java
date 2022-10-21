package com.metanonia.websocketdemo.config;

import com.metanonia.websocketdemo.websocket.KafkaWebSocketHandler;
import com.metanonia.websocketdemo.websocket.MyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/** WebSocket Configuration
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketHandler(), "/ws")
                .addHandler(kafkaSocketHandler(), "/ws-kafka")
                .setAllowedOrigins("*");
    }

    @Bean
    MyWebSocketHandler webSocketHandler() {
        return new MyWebSocketHandler();
    }

    @Bean
    KafkaWebSocketHandler kafkaSocketHandler() {
        return new KafkaWebSocketHandler();
    }
}

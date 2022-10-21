package com.metanonia.websocketdemo.websocket;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.metanonia.websocketdemo.component.Producer;
import com.metanonia.websocketdemo.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class KafkaWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    Producer producer;

    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        String sessionId = session.getId();
        sessions.remove(sessionId);
        log.info("========== afterConnectionClosed :" + sessionId + " , " + status.getReason());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        String sessionId = session.getId();
        sessions.put(sessionId, session);
        log.info("========== afterConnectionEstablished :" + sessionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        log.info("========= handleTextMessage : " + message.getPayload());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionId", session.getId());
        jsonObject.put("message", message.getPayload());
        producer.sendMessage(topicName, jsonObject.toString());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        log.info(exception.toString());
    }

    private void sendMessage(WebSocketSession session, String paylad) {
        log.info("@@@@@@SendMessage: " + paylad);
        TextMessage message = new TextMessage(paylad);
        try {
            session.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToAll(String payload) {
        sessions.forEach((sessionId, webSocketSession) -> this.sendMessage(webSocketSession, payload));
    }
}

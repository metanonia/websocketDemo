package com.metanonia.websocketdemo.controller;


import com.metanonia.websocketdemo.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    @MessageMapping("/hello")
    public void message(Message message) {
        simpleMessageSendingOperations.convertAndSend("/sub/channel/" + message.getChannelId(), message);
    }
}

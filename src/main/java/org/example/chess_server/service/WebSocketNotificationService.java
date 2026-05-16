package org.example.chess_server.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyGameUpdate(String gameId, String message) {
        messagingTemplate.convertAndSend(
                "/topic/game/" + gameId,
                message
        );
    }
}
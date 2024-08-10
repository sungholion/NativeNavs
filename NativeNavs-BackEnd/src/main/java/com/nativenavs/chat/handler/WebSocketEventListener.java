package com.nativenavs.chat.handler;

import com.nativenavs.chat.config.WebSocketConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebSocketConfig webSocketConfig;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        int roomId = getRoomIdFromSession(headerAccessor); // Implement this method to extract roomId from the session
        webSocketConfig.handleUserConnect(roomId, sessionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        int roomId = getRoomIdFromSession(headerAccessor); // Implement this method to extract roomId from the session
        webSocketConfig.handleUserDisconnect(roomId, sessionId);
    }

    private int getRoomIdFromSession(StompHeaderAccessor headerAccessor) {
        // You need to implement logic to extract the room ID from the session or headers
        // For example, if roomId is passed in headers:
        return Integer.parseInt(headerAccessor.getNativeHeader("roomId").get(0));
    }
}
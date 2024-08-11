package com.nativenavs.chat.handler;

import com.nativenavs.chat.config.WebSocketConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

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
        webSocketConfig.handleUserDisconnect(sessionId); // Use the sessionId to handle disconnect
    }

    private int getRoomIdFromSession(StompHeaderAccessor headerAccessor) {
        // Log all headers to see what is actually being received
        System.out.println("Received WebSocket headers: " + headerAccessor.toNativeHeaderMap());

        List<String> roomIdHeader = headerAccessor.getNativeHeader("roomId");
        if (roomIdHeader == null || roomIdHeader.isEmpty()) {
            throw new IllegalArgumentException("Missing roomId header in WebSocket session");
        }
        return Integer.parseInt(roomIdHeader.get(0));
        //
    }
}
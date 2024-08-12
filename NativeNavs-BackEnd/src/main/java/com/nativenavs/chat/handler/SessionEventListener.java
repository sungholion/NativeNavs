package com.nativenavs.chat.handler;

import com.nativenavs.chat.config.WebSocketConfig;
import com.nativenavs.chat.dto.UserStatusDTO;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

public class SessionEventListener {

    private final SubProtocolWebSocketHandler subProtocolWebSocketHandler;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final WebSocketConfig webSocketConfig; // WebSocketConfig 추가

    public SessionEventListener(WebSocketHandler webSocketHandler, SimpMessageSendingOperations simpMessageSendingOperations, WebSocketConfig webSocketConfig) {
        if (webSocketHandler instanceof SubProtocolWebSocketHandler) {
            this.subProtocolWebSocketHandler = (SubProtocolWebSocketHandler) webSocketHandler;
        } else {
            throw new IllegalArgumentException("webSocketHandler must be an instance of SubProtocolWebSocketHandler");
        }
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.webSocketConfig = webSocketConfig; // WebSocketConfig 초기화
    }

    @EventListener
    public void sessionConnectedEventHandler(SessionConnectedEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        Integer roomId = webSocketConfig.getRoomIdBySessionId(sessionId); // roomId 가져오기
        if (roomId != null) {
            webSocketConfig.handleUserConnect(roomId, sessionId);
            simpMessageSendingOperations.convertAndSend("/status/room/" + roomId, new UserStatusDTO(sessionId, "CONNECTED"));
        }
        System.out.println("SessionConnectedEvent = " + event);
    }

    @EventListener
    public void sessionDisconnectEventHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        Integer roomId = webSocketConfig.getRoomIdBySessionId(sessionId); // roomId 가져오기
        if (roomId != null) {
            webSocketConfig.handleUserDisconnect(sessionId);
            simpMessageSendingOperations.convertAndSend("/status/room/" + roomId, new UserStatusDTO(sessionId, "DISCONNECTED"));
        }
        System.out.println("SessionDisconnectEvent = " + event);
    }
}

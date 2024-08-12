package com.nativenavs.chat.handler;

import com.nativenavs.chat.service.ConnectionService;
import com.nativenavs.chat.dto.UserStatusDTO;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

public class SessionEventListener {

    private final SubProtocolWebSocketHandler subProtocolWebSocketHandler;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final ConnectionService connectionService;

    public SessionEventListener(SubProtocolWebSocketHandler subProtocolWebSocketHandler, SimpMessageSendingOperations simpMessageSendingOperations, ConnectionService connectionService) {
        this.subProtocolWebSocketHandler = subProtocolWebSocketHandler;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.connectionService = connectionService;
    }

    @EventListener
    public void sessionConnectedEventHandler(SessionConnectedEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        Integer roomId = connectionService.getRoomIdBySessionId(sessionId);
        if (roomId != null) {
            connectionService.handleUserConnect(roomId, sessionId);
            int userCount = connectionService.getConnectedUserCount(roomId);
            simpMessageSendingOperations.convertAndSend("/status/room/" + roomId, new UserStatusDTO(sessionId, "CONNECTED", userCount));
        }
        System.out.println("SessionConnectedEvent = " + event);
    }

    @EventListener
    public void sessionDisconnectEventHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        Integer roomId = connectionService.getRoomIdBySessionId(sessionId);
        if (roomId != null) {
            connectionService.handleUserDisconnect(sessionId);
            int userCount = connectionService.getConnectedUserCount(roomId);
            simpMessageSendingOperations.convertAndSend("/status/room/" + roomId, new UserStatusDTO(sessionId, "DISCONNECTED", userCount));
        }
        System.out.println("SessionDisconnectEvent = " + event);
    }
}

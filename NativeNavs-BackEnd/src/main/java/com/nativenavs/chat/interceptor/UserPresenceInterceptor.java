package com.nativenavs.chat.interceptor;

import com.nativenavs.chat.dto.UserStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
public class UserPresenceInterceptor implements ChannelInterceptor {

    private final SimpMessagingTemplate messagingTemplate;

    private final ConcurrentMap<Integer, ConcurrentMap<String, Boolean>> connectedUsers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> sessionIdToRoomId = new ConcurrentHashMap<>();

    @Override
    public Message<?> preSend(Message<?> message, org.springframework.messaging.MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        String sessionId = headerAccessor.getSessionId();
        StompCommand command = headerAccessor.getCommand();

        if (StompCommand.SUBSCRIBE.equals(command)) {
            int roomId = getRoomIdFromDestination(headerAccessor.getDestination());
            handleUserConnect(roomId, sessionId);

            if (twoUserConnected(roomId)) {
                broadcastUserStatus(roomId, true);
            }
        } else if (StompCommand.UNSUBSCRIBE.equals(command) || StompCommand.DISCONNECT.equals(command)) {
            Integer roomId = sessionIdToRoomId.remove(sessionId);
            handleUserDisconnect(sessionId);

            if (roomId != null && !twoUserConnected(roomId)) {
                broadcastUserStatus(roomId, false);
            }
        }

        return message;
    }

    private void handleUserConnect(int roomId, String sessionId) {
        connectedUsers.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(sessionId, true);
        sessionIdToRoomId.put(sessionId, roomId);
    }

    private void handleUserDisconnect(String sessionId) {
        Integer roomId = sessionIdToRoomId.remove(sessionId);
        if (roomId != null) {
            ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
            if (roomUsers != null) {
                roomUsers.remove(sessionId);
                if (roomUsers.isEmpty()) {
                    connectedUsers.remove(roomId);
                }
            }
        }
    }

    private boolean twoUserConnected(int roomId) {
        ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
        return roomUsers != null && roomUsers.size() == 2;
    }

    private void broadcastUserStatus(int roomId, boolean bothConnected) {
        String destination = "/room/" + roomId + "/status";
        messagingTemplate.convertAndSend(destination, new UserStatusDTO(bothConnected));
    }

    private int getRoomIdFromDestination(String destination) {
        String[] parts = destination.split("/");
        return Integer.parseInt(parts[2]); // Assuming the format is /room/{roomId}
    }
}

package com.nativenavs.chat.service;

import com.nativenavs.chat.dto.UserCountDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ConnectionService {

    private final ConcurrentMap<Integer, ConcurrentMap<String, Boolean>> connectedUsers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> sessionIdToRoomId = new ConcurrentHashMap<>();
    private final SimpMessageSendingOperations messagingTemplate;

    public ConnectionService(@Lazy SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public synchronized void handleUserConnect(int roomId, String sessionId) {
        connectedUsers.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(sessionId, true);
        sessionIdToRoomId.put(sessionId, roomId);
        sendUserCount(roomId);
    }

    public synchronized void handleUserDisconnect(String sessionId) {
        Integer roomId = sessionIdToRoomId.remove(sessionId);
        if (roomId != null) {
            ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
            if (roomUsers != null) {
                roomUsers.remove(sessionId);
                if (roomUsers.isEmpty()) {
                    connectedUsers.remove(roomId);
                }
            }
            sendUserCount(roomId);
        }
    }

    public int getConnectedUserCount(int roomId) {
        ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
        return roomUsers != null ? roomUsers.size() : 0;
    }

    public Integer getRoomIdBySessionId(String sessionId) {
        return sessionIdToRoomId.get(sessionId);
    }

    private void sendUserCount(int roomId) {
        int userCount = getConnectedUserCount(roomId);
        messagingTemplate.convertAndSend("/status/room/" + roomId, new UserCountDTO(roomId, userCount));
    }
}

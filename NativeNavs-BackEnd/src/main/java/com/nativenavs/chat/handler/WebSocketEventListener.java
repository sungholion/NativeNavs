package com.nativenavs.chat.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

//    private final SimpMessagingTemplate messagingTemplate;
//
//    // Store connected users by room ID
//    private final ConcurrentMap<Integer, ConcurrentMap<String, Boolean>> connectedUsers = new ConcurrentHashMap<>();
//    private final ConcurrentMap<String, Integer> sessionIdToRoomId = new ConcurrentHashMap<>();
//
//    @EventListener
//    public synchronized void handleWebSocketConnectListener(SessionConnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headerAccessor.getSessionId();
//        int roomId = getRoomIdFromSession(headerAccessor);
//
//        handleUserConnect(roomId, sessionId);
//        broadcastBothConnectedStatus(roomId);
//    }
//
//    @EventListener
//    public synchronized void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headerAccessor.getSessionId();
//        Integer roomId = getRoomIdForSession(sessionId);
//
//        if (roomId != null) {
//            handleUserDisconnect(sessionId);
//            broadcastBothConnectedStatus(roomId);
//        }
//    }
//
//    @EventListener
//    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String destination = headerAccessor.getDestination();
//
//        if (destination != null && destination.startsWith("/room/")) {
//            int roomId = extractRoomIdFromDestination(destination);
//            boolean bothConnected = twoUserConnected(roomId);
//
//            // Send the current connection status back to the client
//            messagingTemplate.convertAndSend(destination + "/status", new UserStatusDTO(bothConnected));
//        }
//    }
//
//    private void handleUserConnect(int roomId, String sessionId) {
//        connectedUsers.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(sessionId, true);
//        sessionIdToRoomId.put(sessionId, roomId);
//        System.out.println("User connected: sessionId=" + sessionId + ", roomId=" + roomId);
//        System.out.println("Connected users: " + connectedUsers);
//    }
//
//    private void handleUserDisconnect(String sessionId) {
//        Integer roomId = sessionIdToRoomId.remove(sessionId);
//        if (roomId != null) {
//            ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
//            if (roomUsers != null) {
//                roomUsers.remove(sessionId);
//                if (roomUsers.isEmpty()) {
//                    connectedUsers.remove(roomId);
//                }
//            }
//            System.out.println("User disconnected: sessionId=" + sessionId + ", roomId=" + roomId);
//            System.out.println("Connected users: " + connectedUsers);
//        }
//    }
//
//    public boolean twoUserConnected(int roomId) {
//        ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
//        return roomUsers != null && roomUsers.size() == 2;
//    }
//
//    private void broadcastBothConnectedStatus(int roomId) {
//        boolean bothConnected = twoUserConnected(roomId);
//        String destination = "/room/" + roomId + "/status";
//        System.out.println("Broadcasting bothConnected=" + bothConnected + " to room " + roomId);
//        messagingTemplate.convertAndSend(destination, new UserStatusDTO(bothConnected));
//    }

//    private int getRoomIdFromSession(StompHeaderAccessor headerAccessor) {
//        List<String> roomIdHeader = headerAccessor.getNativeHeader("roomId");
//        if (roomIdHeader == null || roomIdHeader.isEmpty()) {
//            throw new IllegalArgumentException("Missing roomId header in WebSocket session");
//        }
//        return Integer.parseInt(roomIdHeader.get(0));
//    }
//
//    private int extractRoomIdFromDestination(String destination) {
//        String[] parts = destination.split("/");
//        return Integer.parseInt(parts[2]); // Assuming the format is /room/{roomId}
//    }
//
//    private Integer getRoomIdForSession(String sessionId) {
//        return sessionIdToRoomId.get(sessionId);
//    }
}
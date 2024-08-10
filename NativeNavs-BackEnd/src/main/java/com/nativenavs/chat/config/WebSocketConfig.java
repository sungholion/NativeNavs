package com.nativenavs.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Configuration
@EnableWebSocket    // 웹소켓 서버 사용
@EnableWebSocketMessageBroker   // STOMP 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Store connected users by room ID
    private final ConcurrentMap<Integer, ConcurrentMap<String, Boolean>> connectedUsers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> sessionIdToRoomId = new ConcurrentHashMap<>();

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/ws-stomp")
                .setAllowedOriginPatterns("*")//SockJS 연결 주소
                .withSockJS(); //버전 낮은 브라우저에서도 적용 가능
        // 주소 : ws://localhost:8080/ws-stomp
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/send");       //클라이언트에서 보낸 메세지를 받을 prefix
        registry.enableSimpleBroker("/room");    //해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
    }

    // Method to handle user connection
    public void handleUserConnect(int roomId, String sessionId) {
        connectedUsers.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(sessionId, true);
        sessionIdToRoomId.put(sessionId, roomId); // Store the mapping
    }

    // Method to handle user disconnection
    public void handleUserDisconnect(String sessionId) {
        Integer roomId = sessionIdToRoomId.remove(sessionId);
        if (roomId != null) {
            ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
            if (roomUsers != null) {
                roomUsers.remove(sessionId);
                if (roomUsers.isEmpty()) {
                    connectedUsers.remove(roomId);
                }
            } else {
                System.out.println("Session ID " + sessionId + " does not exist in the session-to-room mapping.");
            }
        }
    }

    // Check if a user is connected to a room
    public boolean isUserConnected(int roomId) {
        ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
        return roomUsers != null && !roomUsers.isEmpty();
    }
}
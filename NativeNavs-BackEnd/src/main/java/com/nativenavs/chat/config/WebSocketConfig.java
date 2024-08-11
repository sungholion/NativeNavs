package com.nativenavs.chat.config;

import com.nativenavs.chat.interceptor.UserPresenceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocket    // 웹소켓 서버 사용
@EnableWebSocketMessageBroker   // STOMP 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final UserPresenceInterceptor userPresenceInterceptor;

    // Constructor injection
    public WebSocketConfig(@Lazy SimpMessagingTemplate messagingTemplate, UserPresenceInterceptor userPresenceInterceptor) {
        // Store connected users by room ID
        //    private final ConcurrentMap<Integer, ConcurrentMap<String, Boolean>> connectedUsers = new ConcurrentHashMap<>();
        //    private final ConcurrentMap<String, Integer> sessionIdToRoomId = new ConcurrentHashMap<>();
        this.userPresenceInterceptor = userPresenceInterceptor;
    }
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

    @Override
    public void configureClientInboundChannel(org.springframework.messaging.simp.config.ChannelRegistration registration) {
        registration.interceptors(userPresenceInterceptor);  // Add the interceptor
    }

//    // Method to handle user connection
//    public void handleUserConnect(int roomId, String sessionId) {
//        connectedUsers.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>()).put(sessionId, true);
//        sessionIdToRoomId.put(sessionId, roomId); // Store the mapping
//        broadcastUserStatus(roomId);  // Broadcast the connection status after handling connection
//    }
//
//    // Method to handle user disconnection
//    public void handleUserDisconnect(String sessionId) {
//        Integer roomId = sessionIdToRoomId.remove(sessionId);
//        if (roomId != null) {
//            ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
//            if (roomUsers != null) {
//                roomUsers.remove(sessionId);
//                if (roomUsers.isEmpty()) {
//                    connectedUsers.remove(roomId);
//                }
//            }
//            broadcastUserStatus(roomId);
//        }
//
//    }
//
//    public boolean twoUserConnected(int roomId) {
//        ConcurrentMap<String, Boolean> roomUsers = connectedUsers.get(roomId);
//        // Check if roomUsers is not null and contains exactly 1 connection
//        return roomUsers != null && roomUsers.size() == 2;
//    }
//
//    // Method to broadcast user connection status
//    private void broadcastUserStatus(int roomId) {
//        boolean bothConnected = twoUserConnected(roomId);
//        String destination = "/room/" + roomId + "/status";
//
//        System.out.println("broadcasting used");
//        // Send a message to the specific room's status topic
//        messagingTemplate.convertAndSend(destination, new UserStatusDTO(bothConnected));
//    }


//    public Integer getRoomIdForSession(String sessionId) {
//        return sessionIdToRoomId.get(sessionId);
//    }
}
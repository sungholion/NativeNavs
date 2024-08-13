package com.nativenavs.chat.config;

import com.nativenavs.chat.interceptor.UserPresenceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@EnableWebSocketMessageBroker
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketConfig(@Lazy SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

//    private final @Lazy UserPresenceInterceptor userPresenceInterceptor;

//    // Constructor injection
//    public WebSocketConfig(@Lazy SimpMessagingTemplate messagingTemplate, UserPresenceInterceptor userPresenceInterceptor, SimpMessagingTemplate messagingTemplate1) {
//        this.messagingTemplate = messagingTemplate1;
//        // Store connected users by room ID
//        //    private final ConcurrentMap<Integer, ConcurrentMap<String, Boolean>> connectedUsers = new ConcurrentHashMap<>();
//        //    private final ConcurrentMap<String, Integer> sessionIdToRoomId = new ConcurrentHashMap<>();
//        this.userPresenceInterceptor = userPresenceInterceptor;
//    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/ws-stomp")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/send");
        registry.enableSimpleBroker("/room", "/status");
    }

    @Override
    public void configureClientInboundChannel(org.springframework.messaging.simp.config.ChannelRegistration registration) {
        registration.interceptors(new UserPresenceInterceptor(messagingTemplate));  // Add the interceptor
    }

}

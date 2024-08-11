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

}
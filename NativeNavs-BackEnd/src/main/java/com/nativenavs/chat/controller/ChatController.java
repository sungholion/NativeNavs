package com.nativenavs.chat.controller;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
@CrossOrigin("*")
public class ChatController {
    // DI --------------------------------------------------------------------------------------------------------------

    private final ChatService chatService;

    // API -------------------------------------------------------------------------------------------------------------

    @MessageMapping("/{roomId}") // 여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{roomId}")    // 구독하고 있는 장소로 메시지 전송 (목적지) -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public ChatDTO chatting(@DestinationVariable int roomId, ChatDTO chatDTO) {

        ChatEntity chatEntity = chatService.createChat(     // 채팅 생성 및 저장
                roomId,
                chatDTO.getSenderId(),
                chatDTO.getSenderNickname(),
                chatDTO.getSenderProfileImage(),
                chatDTO.getContent(),
                chatDTO.getSendTime()
        );

        return ChatDTO.toChatDTO(chatEntity);
    }

    // 추가: 특정 채팅을 읽음으로 표시하는 API
    @PostMapping("/read/{chatId}")
    public ResponseEntity<Void> markAsRead(@PathVariable String chatId) {
        chatService.markChatAsRead(chatId);
        System.out.println("marked as read : " + chatId);
        return ResponseEntity.ok().build();
    }
}



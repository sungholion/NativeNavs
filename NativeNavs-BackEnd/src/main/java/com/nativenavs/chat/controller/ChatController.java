package com.nativenavs.chat.controller;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
@CrossOrigin("*") // 아직 고민..
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{roomId}") // 여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{roomId}")    // 구독하고 있는 장소로 메시지 전송 (목적지) -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public ChatDTO chatting(@DestinationVariable int roomId, ChatDTO chatDTO) {

        // 채팅 저장
        ChatEntity chatEntity = chatService.createChat(
                roomId,
                chatDTO.getSenderId(),
                chatDTO.getSenderNickname(),
                chatDTO.getSenderProfileImage(),
                chatDTO.getContent()
        );

        return chatService.toChatDTO(chatEntity);
    }

}



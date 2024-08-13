package com.nativenavs.chat.controller;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    // DI --------------------------------------------------------------------------------------------------------------

    private final ChatService chatService;

    // API -------------------------------------------------------------------------------------------------------------

    @MessageMapping("/{roomId}") // 여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{roomId}")    // 구독하고 있는 장소로 메시지 전송 (목적지) -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public ChatDTO chatting(@DestinationVariable int roomId, ChatDTO chatDTO) {
        log.info("채팅 메시지 수신: roomId={}, senderId={}, content={}", roomId, chatDTO.getSenderId(), chatDTO.getContent());

        ChatEntity chatEntity = chatService.createChat(     // 채팅 생성 및 저장
                roomId,
                chatDTO.getSenderId(),
                chatDTO.getSenderNickname(),
                chatDTO.getSenderProfileImage(),
                chatDTO.getContent(),
                false,
                chatDTO.getSendTime()
        );

        log.info("채팅 메시지 저장 완료: chatId={}, roomId={}", chatEntity.getId(), roomId);

        return ChatDTO.toChatDTO(chatEntity);
    }
}

/*
    리팩토링

 */

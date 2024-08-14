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


    private final ChatService chatService;


    @MessageMapping("/{roomId}")
    @SendTo("/room/{roomId}")
    public ChatDTO chatting(@DestinationVariable int roomId, ChatDTO chatDTO) {
        log.info("채팅 메시지 수신: roomId={}, senderId={}, content={}", roomId, chatDTO.getSenderId(), chatDTO.getContent());

        ChatEntity chatEntity = chatService.createChat(
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


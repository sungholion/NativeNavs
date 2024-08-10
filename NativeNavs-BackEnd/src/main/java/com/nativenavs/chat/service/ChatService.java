package com.nativenavs.chat.service;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.event.ChatCreatedEvent;
import com.nativenavs.chat.repository.ChatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    // DI --------------------------------------------------------------------------------------------------------------

    private final ChatRepository chatRepository;
    private final ApplicationEventPublisher eventPublisher;
    // Method ----------------------------------------------------------------------------------------------------------

    @Transactional
    public ChatEntity createChat(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, String sendTime) {

        ChatEntity chatEntity = chatRepository.save(ChatEntity.createChat(
                roomId,
                senderId,
                senderNickname,
                senderProfileImage,
                content,
                false,
                sendTime
        ));

        eventPublisher.publishEvent(new ChatCreatedEvent(roomId, content, sendTime));

        return chatEntity;
    }

    public List<ChatDTO> findAllChatByRoomId(int roomId, String token) {
        return chatRepository.findAllByRoomId(roomId).stream()
                .map(chatEntity -> ChatDTO.builder()
                        .id(chatEntity.getId().toHexString())
                        .roomId(chatEntity.getRoomId())
                        .senderId(chatEntity.getSenderId())
                        .senderNickname(chatEntity.getSenderNickname())
                        .senderProfileImage(chatEntity.getSenderProfileImage())
                        .content(chatEntity.getContent())
                        .isRead(chatEntity.isRead())
                        .sendTime(chatEntity.getSendTime())
                        .build())
                .collect(Collectors.toList());
    }

}
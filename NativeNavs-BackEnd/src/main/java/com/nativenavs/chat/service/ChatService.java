package com.nativenavs.chat.service;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.event.ChatCreatedEvent;
import com.nativenavs.chat.repository.ChatRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ChatEntity createChat(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, boolean messageChecked, String sendTime) {
        ChatEntity chatEntity = ChatEntity.createChat(
                roomId,
                senderId,
                senderNickname,
                senderProfileImage,
                content,
                messageChecked,
                sendTime
        );

        chatEntity = chatRepository.save(chatEntity);
        eventPublisher.publishEvent(new ChatCreatedEvent(roomId, content, sendTime));

        return chatEntity;
    }

    public void markAllChatsAsReadInRoom(int roomId) {
        List<ChatEntity> unreadChats = chatRepository.findAllByRoomId(roomId).stream()
                .filter(chatEntity -> !chatEntity.isMessageChecked())
                .toList();

        for (ChatEntity chatEntity : unreadChats) {
            chatEntity.markAsRead();
            chatRepository.save(chatEntity);
        }

        notifyClientsAboutReadStatus(roomId);
    }

    private void notifyClientsAboutReadStatus(int roomId) {
        messagingTemplate.convertAndSend("/room/" + roomId + "/read-status", "Messages have been read");
    }

    public List<ChatDTO> findAllChatByRoomId(int roomId, String token) {
        return chatRepository.findAllByRoomId(roomId).stream()
                .map(chatEntity -> {
                    chatEntity.markAsRead();
                    chatRepository.save(chatEntity);

                    return ChatDTO.builder()
                            .id(chatEntity.getId().toHexString())
                            .roomId(chatEntity.getRoomId())
                            .senderId(chatEntity.getSenderId())
                            .senderNickname(chatEntity.getSenderNickname())
                            .senderProfileImage(chatEntity.getSenderProfileImage())
                            .content(chatEntity.getContent())
                            .messageChecked(chatEntity.isMessageChecked())
                            .sendTime(chatEntity.getSendTime())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void markChatAsRead(String chatId) {
        ChatEntity chatEntity = chatRepository.findById(new ObjectId(chatId).getTimestamp())
                .orElseThrow(() -> new NoSuchElementException("Chat not found with id: " + chatId));
        chatEntity.markAsRead();
        chatRepository.save(chatEntity);

        notifyClientsAboutReadStatus(chatEntity.getRoomId());
    }
}

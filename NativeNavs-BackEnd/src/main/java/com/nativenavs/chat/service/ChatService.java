package com.nativenavs.chat.service;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.entity.RoomEntity;
import com.nativenavs.chat.repository.ChatRepository;
import com.nativenavs.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;

    //채팅 생성

    public ChatDTO saveChat(int roomId, ChatDTO chatDTO) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID: " + roomId));

        ChatEntity chatEntity = ChatEntity.builder()
                .roomId(roomId)
                .senderId(chatDTO.getSenderId())
                .senderNickname(chatDTO.getSenderNickname())
                .senderProfileImage(chatDTO.getSenderProfileImage())
                .content(chatDTO.getContent())
                .isRead(chatDTO.isRead())
                .sendTime(System.currentTimeMillis())
                .build();

        chatEntity = chatRepository.save(chatEntity);

        return ChatDTO.builder()
                .id(chatEntity.getId().toHexString())
                .roomId(chatEntity.getRoomId())
                .senderId(chatEntity.getSenderId())
                .senderNickname(chatEntity.getSenderNickname())
                .senderProfileImage(chatEntity.getSenderProfileImage())
                .content(chatEntity.getContent())
                .isRead(chatEntity.isRead())
                .sendTime(chatEntity.getSendTime())
                .build();
    }

    public ChatEntity createChat(int roomId, int senderId, String senderNickname, String senderProfileImage, String content) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID: " + roomId)); // 방 찾기 -> 없는 방일 경우 예외처리

        return chatRepository.save(ChatEntity.createChat(
                roomId,
                senderId,
                senderNickname,
                senderProfileImage,
                content,
                false // Assuming new messages are not read initially
        ));
    }

    /**
     * 채팅방 채팅내용 불러오기
     * @param roomId 채팅방 id
     */
    public List<ChatDTO> findAllChatByRoomId(int roomId) {
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
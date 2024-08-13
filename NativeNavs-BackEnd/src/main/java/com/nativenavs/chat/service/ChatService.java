package com.nativenavs.chat.service;

import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.event.ChatCreatedEvent;
import com.nativenavs.chat.interceptor.UserPresenceInterceptor;
import com.nativenavs.chat.repository.ChatRepository;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    // DI --------------------------------------------------------------------------------------------------------------

    private final ChatRepository chatRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final @Lazy UserPresenceInterceptor userPresenceInterceptor;
    private final SimpMessagingTemplate messagingTemplate;
    // Method ----------------------------------------------------------------------------------------------------------

    @Transactional
    public ChatEntity createChat(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, boolean messageChecked, String sendTime) {
        ChatEntity chatEntity;

        if(content.equals("문의 신청합니다.")){
            chatEntity = chatRepository.save(ChatEntity.createChat(
                    roomId,
                    senderId,
                    senderNickname,
                    senderProfileImage,
                    content,
                    messageChecked,  // If connected, mark as read
                    sendTime
            ));

        }

        else{
            boolean twoUserConnected = userPresenceInterceptor.twoUserConnected(roomId); // 한명만 연결인지

            System.out.println("twoUserConnected: " + twoUserConnected);

            boolean resultIsRead = false;

            if(twoUserConnected) {
                resultIsRead = true;
                markAllChatsAsReadInRoom(roomId);
            }



            chatEntity = chatRepository.save(ChatEntity.createChat(
                    roomId,
                    senderId,
                    senderNickname,
                    senderProfileImage,
                    content,
                    resultIsRead,  // 두명 다 연결이면 읽음 처리
                    sendTime
            ));


            System.out.println("is Read : " + chatEntity.isMessageChecked());

        }
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
        // Send a WebSocket message to notify clients about the read status change
        messagingTemplate.convertAndSend("/room/" + roomId + "/read-status", "Messages have been read");
    }


    public List<ChatDTO> findAllChatByRoomId(int roomId, String token) {

        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        UserDTO userDTO = userService.searchByEmail(email); // token으로부터 현재 로그인한 userDTO 찾기


        return chatRepository.findAllByRoomId(roomId).stream()
                .map(chatEntity -> {
                    // 채팅 조회 시 읽음 처리

                    if(userDTO.getId() != chatEntity.getSenderId()){
                        chatEntity.markAsRead();
                    }

                    chatRepository.save(chatEntity);  // 읽음 상태 저장 or 미저장

                    return ChatDTO.builder()
                            .id(chatEntity.getId().toHexString())
                            .roomId(chatEntity.getRoomId())
                            .senderId(chatEntity.getSenderId())
                            .senderNickname(chatEntity.getSenderNickname())
                            .senderProfileImage(chatEntity.getSenderProfileImage())
                            .content(chatEntity.getContent())
                            .messageChecked(chatEntity.getMessageChecked())
                            .sendTime(chatEntity.getSendTime())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 추가: 특정 채팅을 읽음으로 표시하는 메서드
    @Transactional
    public void markChatAsRead(String chatId) {
        ChatEntity chatEntity = chatRepository.findById(new ObjectId(chatId).getTimestamp())
                .orElseThrow(() -> new NoSuchElementException("Chat not found with id: " + chatId));
        chatEntity.markAsRead();

        System.out.println("ChatEntity에 read 여긴가 " + chatEntity);
        chatRepository.save(chatEntity);

        notifyClientsAboutReadStatus(chatEntity.getRoomId());
    }

}
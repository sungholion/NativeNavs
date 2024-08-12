package com.nativenavs.chat.service;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.chat.config.WebSocketConfig;
import com.nativenavs.chat.dto.ChatDTO;
import com.nativenavs.chat.entity.ChatEntity;
import com.nativenavs.chat.event.ChatCreatedEvent;
import com.nativenavs.chat.repository.ChatRepository;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
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
    private final WebSocketConfig webSocketConfig;
    private final UserService userService;
    // Method ----------------------------------------------------------------------------------------------------------

    @Transactional
    public ChatEntity createChat(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, boolean messageChecked, String sendTime) {

            boolean twoUserConnected = webSocketConfig.twoUserConnected(roomId); // 한명만 연결인지

            boolean resultIsRead = false;

            if(twoUserConnected) {
                resultIsRead = true;
            }

            if(content.equals("문의 신청합니다")){
                resultIsRead = false;
                System.out.println("여기는 문의 신청드립니다야!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + resultIsRead);
            }

            ChatEntity chatEntity = chatRepository.save(ChatEntity.createChat(
                    roomId,
                    senderId,
                    senderNickname,
                    senderProfileImage,
                    content,
                    resultIsRead,  // 두명 다 연결이면 읽음 처리
                    sendTime
            ));

            eventPublisher.publishEvent(new ChatCreatedEvent(roomId, content, sendTime));

            return chatEntity;


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
    public void markChatAsRead(String chatId) {
        ChatEntity chatEntity = chatRepository.findById(new ObjectId(chatId).getTimestamp())
                .orElseThrow(() -> new NoSuchElementException("Chat not found with id: " + chatId));
        chatEntity.markAsRead();

        System.out.println("ChatEntity에 read 여긴가 " + chatEntity);
        chatRepository.save(chatEntity);
    }

}
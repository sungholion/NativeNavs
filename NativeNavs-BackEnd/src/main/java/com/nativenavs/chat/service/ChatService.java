package com.nativenavs.chat.service;

import com.nativenavs.auth.jwt.JwtTokenProvider;
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

    private final ChatRepository chatRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;
    private final ConnectionService connectionService;


    @Transactional
    public ChatEntity createChat(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, boolean messageChecked, String sendTime) {

        boolean twoUserConnected = connectionService.getConnectedUserCount(roomId) == 2;
        boolean resultIsRead = false;

        if(twoUserConnected) {
            resultIsRead = true;
        }

        if(content.equals("문의 신청합니다")){
            resultIsRead = false;
        }

        ChatEntity chatEntity = chatRepository.save(ChatEntity.createChat(
                roomId,
                senderId,
                senderNickname,
                senderProfileImage,
                content,
                resultIsRead,
                sendTime
        ));

        eventPublisher.publishEvent(new ChatCreatedEvent(roomId, content, sendTime));

        return chatEntity;

    }

    public List<ChatDTO> findAllChatByRoomId(int roomId, String token) {

        String jwtToken = token.replace("Bearer ", "");
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        UserDTO userDTO = userService.searchByEmail(email);


        return chatRepository.findAllByRoomId(roomId).stream()
                .map(chatEntity -> {


                    if(userDTO.getId() != chatEntity.getSenderId()){
                        chatEntity.markAsRead();
                    }

                    chatRepository.save(chatEntity);

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

    @Transactional
    public void markChatAsRead(String chatId) {
        ChatEntity chatEntity = chatRepository.findById(new ObjectId(chatId).getTimestamp())
                .orElseThrow(() -> new NoSuchElementException("Chat not found with id: " + chatId));
        chatEntity.markAsRead();

        chatRepository.save(chatEntity);
    }

}

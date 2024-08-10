package com.nativenavs.chat.dto;

import com.nativenavs.chat.entity.ChatEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ChatDTO {
    private String id;
    private int roomId;
    private int senderId;
    private String senderNickname;
    private String senderProfileImage;
    private String content;

    private boolean messageChecked;
    private String sendTime;

    public static ChatDTO toChatDTO(ChatEntity chatEntity) {
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
    }
}

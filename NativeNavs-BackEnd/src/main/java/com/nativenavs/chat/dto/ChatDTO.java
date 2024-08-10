package com.nativenavs.chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("isRead")
    private boolean isRead;
    private String sendTime;

    public static ChatDTO toChatDTO(ChatEntity chatEntity) {
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
}

package com.nativenavs.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private boolean isRead;
    private LocalDateTime sendTime;



}

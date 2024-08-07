package com.nativenavs.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private int id;
    private int tourId;
    private int senderId;
    private String senderNickname;
    private boolean senderIsNav;
    private int receiverId;
    private String receiverNickname;
    private boolean receiverIsNav;
    private String recentMessageContent;
    private long recentMessageTime;

}

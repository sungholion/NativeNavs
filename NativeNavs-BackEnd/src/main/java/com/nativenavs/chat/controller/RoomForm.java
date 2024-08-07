package com.nativenavs.chat.controller;

import lombok.Data;

@Data
public class RoomForm {
    private int tourId;
    private int senderId;
    private String senderNickname;
    private boolean senderIsNav;
    private int receiverId;
    private String receiverNickname;
    private boolean receiverIsNav;
}
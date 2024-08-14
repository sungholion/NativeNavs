package com.nativenavs.chat.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chats")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class ChatEntity {

    @Id
    private ObjectId id;

    private int roomId;

    private int senderId;
    private String senderNickname;
    private String senderProfileImage;
    private String content;
    private Boolean messageChecked;
    private String sendTime;


    @Builder
    public ChatEntity(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, Boolean messageChecked, String sendTime) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.senderProfileImage = senderProfileImage;
        this.content = content;
        this.messageChecked = messageChecked;
        this.sendTime = sendTime;
    }

    @Builder
    public static ChatEntity createChat(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, Boolean messageChecked, String sendTime) {
        return ChatEntity.builder()
                .roomId(roomId)
                .senderId(senderId)
                .senderNickname(senderNickname)
                .senderProfileImage(senderProfileImage)
                .content(content)
                .messageChecked(messageChecked)
                .sendTime(sendTime)
                .build();
    }


    public void markAsRead() {
        this.messageChecked = true;
    }

}
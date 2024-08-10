package com.nativenavs.chat.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatEntity {

    @Id
    private ObjectId id;

    private int roomId;

    private int senderId;
    private String senderNickname;
    private String senderProfileImage;
    private String content;
    private boolean isRead;
    private String sendTime;

    // 이게 필요한가? 없으면 초기화?가 안됬다고 하긴함
    @Builder
    public ChatEntity(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, boolean isRead, String sendTime) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.senderProfileImage = senderProfileImage;
        this.content = content;
        this.isRead = isRead;
        this.sendTime = sendTime;
    }

    @Builder
    public static ChatEntity createChat(int roomId, int senderId, String senderNickname, String senderProfileImage, String content, boolean isRead, String sendTime) {
        return ChatEntity.builder()
                .roomId(roomId)
                .senderId(senderId)
                .senderNickname(senderNickname)
                .senderProfileImage(senderProfileImage)
                .content(content)
                .isRead(isRead)
                .sendTime(sendTime)
                .build();
    }
}
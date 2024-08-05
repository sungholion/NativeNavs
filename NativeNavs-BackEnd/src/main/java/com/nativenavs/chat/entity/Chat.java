package com.nativenavs.chat.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Chat {

    @Id
    private String id;

    private Room room;

    private String sender;

    private String message;

    @CreatedDate
    private LocalDateTime sendDate;

    @Builder
    public Chat(Room room, String sender, String message) {
        this.room = room;
        this.sender = sender;
        this.message = message;
        this.sendDate = LocalDateTime.now();
    }

    /**
     * 채팅 생성
     * @param room 채팅 방
     * @param sender 보낸이
     * @param message 내용
     * @return Chat Entity
     */
    public static Chat createChat(Room room, String sender, String message) {
        return Chat.builder()
                .room(room)
                .sender(sender)
                .message(message)
                .build();
    }
}
package com.nativenavs.chat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;

    @Column(name = "tour_id", nullable = false)
    private int tourId;

    @Column(name = "tour_title", nullable = false)
    private String tourTitle;

    @Column(name = "tour_img_url", nullable = false)
    private String tourImgUrl;

    @Column(name = "tour_region", nullable = false)
    private String tourRegion;

    @Column(name = "sender_id", nullable = false)
    private int senderId;

    @Column(name = "sender_nickname", nullable = false)
    private String senderNickname;

    @Column(name = "sender_is_nav", nullable = false)
    private boolean senderIsNav;

    @Column(name = "receiver_id", nullable = false)
    private int receiverId;

    @Column(name = "receiver_nickname", nullable = false)
    private String receiverNickname;

    @Column(name = "receiver_is_nav", nullable = false)
    private boolean receiverIsNav;

    @Column(name = "recent_message_content")
    private String recentMessageContent;

    @Column(name = "recent_message_time")
    private String recentMessageTime;


    /**
     * 채팅방 생성
     * @param tourId 투어 ID
     * @param senderId 발신자 ID
     * @param senderNickname 발신자 닉네임
     * @param senderIsNav 발신자 NAV 여부
     * @param receiverId 수신자 ID
     * @param receiverNickname 수신자 닉네임
     * @param receiverIsNav 수신자 NAV 여부
     * @return Room Entity
     */
    public static RoomEntity createRoom(int tourId, String tourTitle, String tourImgUrl, String tourRegion, int senderId, String senderNickname, boolean senderIsNav, int receiverId, String receiverNickname, boolean receiverIsNav) {
        return RoomEntity.builder()
                .tourId(tourId)
                .tourTitle(tourTitle)
                .tourImgUrl(tourImgUrl)
                .tourRegion(tourRegion)
                .senderId(senderId)
                .senderNickname(senderNickname)
                .senderIsNav(senderIsNav)
                .receiverId(receiverId)
                .receiverNickname(receiverNickname)
                .receiverIsNav(receiverIsNav)
                .recentMessageContent(null)
                .recentMessageTime(null)
                .build();
    }


}

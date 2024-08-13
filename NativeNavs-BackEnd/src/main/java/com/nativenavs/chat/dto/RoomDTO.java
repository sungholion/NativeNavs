package com.nativenavs.chat.dto;

import com.nativenavs.chat.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private int roomId;
    private int tourId;
    private String tourTitle;
    private String tourImgUrl;
    private String tourRegion;
    private int senderId;   // 메세지를 처음에 요청하는 사람. 즉 Trav
    private String senderNickname;
    private boolean senderIsNav;
    private int receiverId; // 메세지를 처음에 받는 사람. 즉 Nav
    private String receiverNickname;
    private boolean receiverIsNav;
    private String recentMessageContent;    // 채팅방 목록에서 최근 메세지를 띄우기 위함
    private String recentMessageTime;       // 채팅방 목록에서 최근 메세지 시간을 띄우기 위함

    public static RoomDTO toRoomDTO(RoomEntity roomEntity){
        return RoomDTO.builder()
                .roomId(roomEntity.getRoomId())
                .tourId(roomEntity.getTourId())
                .tourTitle(roomEntity.getTourTitle())
                .tourImgUrl(roomEntity.getTourImgUrl())
                .tourRegion(roomEntity.getTourRegion())
                .senderId(roomEntity.getSenderId())
                .senderNickname(roomEntity.getSenderNickname())
                .senderIsNav(roomEntity.isSenderIsNav())
                .receiverId(roomEntity.getReceiverId())
                .receiverNickname(roomEntity.getReceiverNickname())
                .receiverIsNav(roomEntity.isReceiverIsNav())
                .recentMessageContent(roomEntity.getRecentMessageContent())
                .recentMessageTime(roomEntity.getRecentMessageTime())
                .build();
    }
}

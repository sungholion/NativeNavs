package com.nativenavs.chat.dto;

import com.nativenavs.chat.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private int roomId;
    private int tourId;
    private String tourTitle;
    private String tourImgUrl;

    private int senderId;
    private String senderNickname;
    private boolean senderIsNav;
    private int receiverId;
    private String receiverNickname;
    private boolean receiverIsNav;
    private String recentMessageContent;
    private long recentMessageTime;


    public static RoomDTO toRoomDTO(RoomEntity roomEntity){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomId(roomEntity.getId());
        roomDTO.setTourId(roomEntity.getTourId());
        roomDTO.setTourTitle(roomEntity.getTourTitle());
        roomDTO.setTourImgUrl(roomEntity.getTourImgUrl());
        roomDTO.setSenderId(roomEntity.getSenderId());
        roomDTO.setSenderNickname(roomEntity.getSenderNickname());
        roomDTO.setSenderIsNav(roomEntity.isSenderIsNav());
        roomDTO.setReceiverId(roomEntity.getReceiverId());
        roomDTO.setReceiverNickname(roomEntity.getReceiverNickname());
        roomDTO.setReceiverIsNav(roomEntity.isReceiverIsNav());
        roomDTO.setRecentMessageContent(roomEntity.getRecentMessageContent());
        roomDTO.setRecentMessageTime(roomEntity.getRecentMessageTime());

        return roomDTO;
    }

}

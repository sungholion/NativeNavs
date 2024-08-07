package com.nativenavs.chat.service;

import com.nativenavs.chat.entity.RoomEntity;
import com.nativenavs.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    /**
     * 모든 채팅방 찾기
     */
    public List<RoomEntity> findAllRoom() {
        return roomRepository.findAll();
    }

    /**
     * 특정 채팅방 찾기
     * @param id room_idv
     */
    public RoomEntity findRoomById(int id) {
        return roomRepository.findById(id).orElseThrow();
    }

    public RoomEntity createRoom(int tourId, int senderId, String senderNickname, boolean senderIsNav, int receiverId, String receiverNickname, boolean receiverIsNav) {
        return roomRepository.save(RoomEntity.createRoom(tourId, senderId, senderNickname, senderIsNav, receiverId, receiverNickname, receiverIsNav));
    }


}

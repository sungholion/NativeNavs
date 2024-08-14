package com.nativenavs.chat.repository;

import com.nativenavs.chat.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    List<RoomEntity> findAllBySenderId(int senderId);
    List<RoomEntity> findAllByReceiverId(int receiverId);
    RoomEntity findByTourIdAndSenderId(int tourId, int senderId);
    RoomEntity findByTourId(int tourId);
}

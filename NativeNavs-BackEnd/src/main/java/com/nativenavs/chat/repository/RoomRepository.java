package com.nativenavs.chat.repository;

import com.nativenavs.chat.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
}

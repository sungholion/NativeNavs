package com.nativenavs.chat.repository;

import com.nativenavs.chat.entity.ChatEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepository extends CrudRepository<ChatEntity, Integer> {

    List<ChatEntity> findAllByRoomId(int roomId);
}

package com.nativenavs.chat.repository;

import com.nativenavs.chat.entity.ChatEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatEntity, Integer> {

    List<ChatEntity> findAllByRoomId(int roomId);
}

package com.nativenavs.stamp.repository;

import com.nativenavs.stamp.entity.UserStampEntity;
import com.nativenavs.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStampRepository extends JpaRepository<UserStampEntity, Integer> {
    List<UserStampEntity> findAllByUser(UserEntity user);
}

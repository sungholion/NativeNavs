package com.nativenavs.user.repository;

import com.nativenavs.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    String findFcmTokenById(int id);

    @Modifying
    @Query("UPDATE UserEntity u SET u.fcmToken = :fcmToken WHERE u.id = :id")
    void updateFcmToken(@Param("id") int id, @Param("fcmToken") String fcmToken);

}

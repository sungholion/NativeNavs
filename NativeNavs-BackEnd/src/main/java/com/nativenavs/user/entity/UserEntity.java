package com.nativenavs.user.entity;

import com.nativenavs.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 투어 ID

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false)
    private Boolean isNav;

    @Column(nullable = false)
    private Date birth;

    @Column(nullable = false, length = 50)
    private String userLanguage;

    @Column(nullable = false, length = 50)
    private String nation;

    @Column(nullable = false, length = 255) // 254 255
    private String image;

    @Column(nullable = true)
    private int navReviewCount; // 가이드가 받은 리뷰 총 수

    @Column(nullable = true)
    private float navReviewAverage; // 가이드가 받은 리뷰 총 평점

    @Column(nullable = true)
    private int travReservationCount;   // 여행자가 경험한 여행 총 수

    @Column(nullable = false)
    private boolean isKorean;

    @Column(nullable = false, length = 100)
    private String device;

    // DTO -> Entity
    public static UserEntity toSaveEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setId(userDTO.getId());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setName(userDTO.getName());
        userEntity.setNickname(userDTO.getNickname());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setIsNav(userDTO.getIsNav());
        userEntity.setBirth(userDTO.getBirth());
        userEntity.setUserLanguage(userDTO.getUserLanguage());
        userEntity.setNation(userDTO.getNation());
        userEntity.setImage(userDTO.getImage());
        userEntity.setNavReviewCount(userDTO.getNavReviewCount());
        userEntity.setNavReviewAverage(userDTO.getNavReviewAverage());
        userEntity.setTravReservationCount(userDTO.getTravReservationCount());
        userEntity.setKorean(userDTO.isKorean());
        userEntity.setDevice(userDTO.getDevice());

        return userEntity;
    }
}

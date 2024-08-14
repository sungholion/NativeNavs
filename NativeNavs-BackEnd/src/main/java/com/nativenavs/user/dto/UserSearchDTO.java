package com.nativenavs.user.dto;

import com.nativenavs.user.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDTO {
    private int id;
    private String email;
    private String name;
    private String nickname;
    private String phone;
    private Boolean isNav;
    private Date birth;
    private String userLanguage;
    private String nation;
    private String image;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt;
    private boolean isRemoved;
    private int navReviewCount;
    private float navReviewAverage;
    private int travReservationCount;
    private boolean isKorean;
    private String device;
    private String fcmToken;


    public static UserSearchDTO toUserSearchDTO(UserEntity userEntity){
        UserSearchDTO userSearchDTO = new UserSearchDTO();
        userSearchDTO.setId(userEntity.getId());
        userSearchDTO.setEmail(userEntity.getEmail());
        userSearchDTO.setName(userEntity.getName());
        userSearchDTO.setNickname(userEntity.getNickname());
        userSearchDTO.setPhone(userEntity.getPhone());
        userSearchDTO.setIsNav(userEntity.getIsNav());
        userSearchDTO.setBirth(userEntity.getBirth());
        userSearchDTO.setUserLanguage(userEntity.getUserLanguage());
        userSearchDTO.setNation(userEntity.getNation());
        userSearchDTO.setImage(userEntity.getImage());
        userSearchDTO.setCreatedAt(userEntity.getCreatedAt());
        userSearchDTO.setUpdatedAt(userEntity.getUpdatedAt());
        userSearchDTO.setNavReviewCount(userEntity.getNavReviewCount());
        userSearchDTO.setNavReviewAverage(userEntity.getNavReviewAverage());
        userSearchDTO.setTravReservationCount(userEntity.getTravReservationCount());
        userSearchDTO.setKorean(userEntity.isKorean());
        userSearchDTO.setDevice(userEntity.getDevice());
        userSearchDTO.setFcmToken(userEntity.getFcmToken());

        return userSearchDTO;
    }
}

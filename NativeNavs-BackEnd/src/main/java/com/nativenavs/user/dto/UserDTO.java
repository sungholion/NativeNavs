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
public class UserDTO {
    private int id;
    private String email;
    private String password;
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

    // Entity -> DTO
    public static UserDTO toUserDTO(UserEntity userEntity){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setName(userEntity.getName());
        userDTO.setNickname(userEntity.getNickname());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setIsNav(userEntity.getIsNav());
        userDTO.setBirth(userEntity.getBirth());
        userDTO.setUserLanguage(userEntity.getUserLanguage());
        userDTO.setNation(userEntity.getNation());
        userDTO.setImage(userEntity.getImage());
        userDTO.setCreatedAt(userEntity.getCreatedAt());
        userDTO.setUpdatedAt(userEntity.getUpdatedAt());
        userDTO.setNavReviewCount(userEntity.getNavReviewCount());
        userDTO.setNavReviewAverage(userEntity.getNavReviewAverage());
        userDTO.setTravReservationCount(userEntity.getTravReservationCount());
        userDTO.setKorean(userEntity.isKorean());
        userDTO.setDevice(userEntity.getDevice());

        return userDTO;
    }
}



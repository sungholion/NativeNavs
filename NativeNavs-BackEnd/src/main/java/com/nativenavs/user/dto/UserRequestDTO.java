package com.nativenavs.user.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
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
    private MultipartFile image;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt;
    private boolean isRemoved;
    private int navReviewCount;
    private float navReviewAverage;
    private int travReservationCount;
    private boolean isKorean;
    private String device;
}



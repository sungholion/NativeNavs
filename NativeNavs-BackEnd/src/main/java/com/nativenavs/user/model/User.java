package com.nativenavs.user.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class User {
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
    private LocalDateTime createdAt ;   //LocalDateTime과 Date 사용 고민...
    private LocalDateTime updatedAt;
    private boolean isRemoved;
    private int navReviewCount;
    private float navReviewAverage;
    private int travReservationCount;
    private boolean isKorean;
    private String device;
}



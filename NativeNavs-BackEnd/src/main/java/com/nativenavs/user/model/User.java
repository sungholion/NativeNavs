package com.nativenavs.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private int id;
    private String email;
    private String authenticationCode;
    private boolean isAuthenticated;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private boolean is_nav;
    private Date birth;
    private String user_language;
    private String nation;
    private String image;
    private Date created_at ;   //LocalDateTime과 사용 고민...
    private Date updated_at;
    private boolean is_removed;
    private int nav_review_count;
    private float nav_review_average;
    private int trav_reservation_count;
    private boolean is_korean;
    private String device;

    @Override
    public String toString() {
        return "User{" +
                "nav_review_count=" + nav_review_count +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", is_nav=" + is_nav +
                ", birth=" + birth +
                ", user_language='" + user_language + '\'' +
                ", nation='" + nation + '\'' +
                ", image='" + image + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", is_removed=" + is_removed +
                ", nav_review_average=" + nav_review_average +
                ", trav_reservation_count=" + trav_reservation_count +
                ", is_korean=" + is_korean +
                ", device='" + device + '\'' +
                '}';
    }
}



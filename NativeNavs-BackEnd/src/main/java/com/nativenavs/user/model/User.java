package com.nativenavs.user.model;

import java.util.Date;

public class User {
    private int id;
    private String email;
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

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isIs_nav() {
        return is_nav;
    }

    public Date getBirth() {
        return birth;
    }

    public String getUser_language() {
        return user_language;
    }

    public String getNation() {
        return nation;
    }

    public String getImage() {
        return image;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public boolean isIs_removed() {
        return is_removed;
    }

    public int getNav_review_count() {
        return nav_review_count;
    }

    public float getNav_review_average() {
        return nav_review_average;
    }

    public int getTrav_reservation_count() {
        return trav_reservation_count;
    }

    public boolean isIs_korean() {
        return is_korean;
    }

    public String getDevice() {
        return device;
    }

    public String getPhone() {return phone; }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setIs_nav(boolean is_nav) {
        this.is_nav = is_nav;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setUser_language(String user_language) {
        this.user_language = user_language;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public void setIs_removed(boolean is_removed) {
        this.is_removed = is_removed;
    }

    public void setNav_review_count(int nav_review_count) {
        this.nav_review_count = nav_review_count;
    }

    public void setNav_review_average(float nav_review_average) {
        this.nav_review_average = nav_review_average;
    }

    public void setTrav_reservation_count(int trav_reservation_count) {
        this.trav_reservation_count = trav_reservation_count;
    }

    public void setIs_korean(boolean is_korean) {
        this.is_korean = is_korean;
    }

    public void setDevice(String device) {
        this.device = device;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }

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



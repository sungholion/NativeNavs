package com.nativenavs.user.entity;

import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.review.entity.ReviewEntity;
import com.nativenavs.stamp.entity.UserStampEntity;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.wishlist.entity.WishlistEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private int id;

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

    @Column(nullable = false, length = 255)
    private String image;

    @Column(nullable = true)
    private int navReviewCount;

    @Column(nullable = true)
    private float navReviewAverage;

    @Column(nullable = true)
    private int travReservationCount;

    @Column(nullable = false)
    private boolean isKorean;

    @Column(nullable = false, length = 100)
    private String device;

    @Column(nullable = false, length = 255)
    private String fcmToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserStampEntity> userStamps;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishlistEntity> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourEntity> tours = new ArrayList<>();

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> guideReservations = new ArrayList<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> participantReservations = new ArrayList<>();

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviewsGiven = new ArrayList<>();

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
//        userEntity.setImage(userDTO.getImage());
        userEntity.setNavReviewCount(userDTO.getNavReviewCount());
        userEntity.setNavReviewAverage(userDTO.getNavReviewAverage());
        userEntity.setTravReservationCount(userDTO.getTravReservationCount());
        userEntity.setKorean(userDTO.isKorean());
        userEntity.setDevice(userDTO.getDevice());

        return userEntity;
    }
}

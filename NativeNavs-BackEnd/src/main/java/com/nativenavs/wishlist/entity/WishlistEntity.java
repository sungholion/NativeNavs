package com.nativenavs.wishlist.entity;

import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="wish")
public class WishlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private TourEntity tour;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 생성자
    public WishlistEntity(UserEntity user, TourEntity tour) {
        this.user = user;
        this.tour = tour;
        this.createdAt = LocalDateTime.now();
    }


}

package com.nativenavs.review.entity;

import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity reviewer; // 리뷰어

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private TourEntity tour; // 투어

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private UserEntity guide; // 가이드

    @Column(name = "score", nullable = false)
    private Integer score; // 리뷰 점수

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description; // 리뷰 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 리뷰 작성 일시

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImageEntity> images; // 리뷰 이미지

}

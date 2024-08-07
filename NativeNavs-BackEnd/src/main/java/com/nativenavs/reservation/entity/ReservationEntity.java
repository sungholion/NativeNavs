package com.nativenavs.reservation.entity;

import com.nativenavs.reservation.enums.ReservationStatus;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Progress")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private UserEntity guide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private TourEntity tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private UserEntity participant;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name="start_at", nullable = false)
    private LocalTime startAt;

    @Column(name="end_at", nullable = false)
    private LocalTime endAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(nullable = false, name ="participant_count",columnDefinition = "INT DEFAULT 1")
    private int participantCount;

    @Column(name="meeting_address", nullable = false)
    private String meetingAddress;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_reviewed", nullable = false,columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isReviewed;

    @Column(name = "tagging-at")
    private LocalDateTime taggingAt;

//    @Column(columnDefinition = "TEXT")
//    private String description; // 추가요청사항

    @Column(name = "meeting_latitude", precision = 10, scale = 8)
    private BigDecimal meetingLatitude;

    @Column(name = "meeting_longitude", precision = 11, scale = 8)
    private BigDecimal meetingLongitude;

}
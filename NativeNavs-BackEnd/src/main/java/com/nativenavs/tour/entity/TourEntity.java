package com.nativenavs.tour.entity;

import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.tour.dto.TourDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name ="tour")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TourEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 투어 ID

    //@ManyToOne
    @Column(name = "user_id", nullable = false)
    private int userId; // 유저 ID

    @Column(nullable = false, length = 100)
    private String title; // 투어 제목

    @Column(name = "thumbnail_image", nullable = false, length = 255)
    private String thumbnailImage; // 썸네일 이미지

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description; // 투어 설명

    @Column(nullable = false, length = 30)
    private String location; // 투어 위치 정보 (시,군)

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int price; // 예상 가격

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // 투어 시작일

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // 투어 종료일

    @Column(name = "review_average", nullable = false, columnDefinition = "FLOAT DEFAULT 0.0")
    private float reviewAverage; // 투어 리뷰 평점

    @Column(name = "review_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int reviewCount; // 투어 리뷰 수

    @Column(name = "max_participant", nullable = false, columnDefinition = "INT DEFAULT 1")
    private int maxParticipant; // 투어 최대 참여 인원

    @Column(name = "is_removed", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isRemoved; // 투어 삭제 여부

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private Set<TourCategoryEntity> tourCategories;

    @OneToMany(mappedBy = "tourId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlanEntity> plans;


    @OneToMany(mappedBy="tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations = new ArrayList<>();

    //DTO -> Entity 로 옮겨닮기
    public static TourEntity toSaveEntity(TourDTO tourDTO){
        TourEntity tourEntity = new TourEntity();
        tourEntity.setTitle(tourDTO.getTitle());
        tourEntity.setThumbnailImage(tourDTO.getThumbnailImage());
        tourEntity.setDescription(tourDTO.getDescription());
        tourEntity.setLocation(tourDTO.getLocation());
        tourEntity.setPrice(tourDTO.getPrice());
        tourEntity.setStartDate(tourDTO.getStartDate());
        tourEntity.setEndDate(tourDTO.getEndDate());
        tourEntity.setReviewAverage(tourDTO.getReviewAverage());
        tourEntity.setReviewCount(tourDTO.getReviewCount());
        tourEntity.setMaxParticipant(tourDTO.getMaxParticipants());
        tourEntity.setRemoved(tourDTO.isRemoved());
        return tourEntity;
    }
}

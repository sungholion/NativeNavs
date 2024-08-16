package com.nativenavs.tour.entity;

import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.dto.TourRequestDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.wishlist.entity.WishlistEntity;
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
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(name = "thumbnail_image", nullable = false, length = 255)
    private String thumbnailImage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 30)
    private String location;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int price;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "review_average", nullable = false, columnDefinition = "FLOAT DEFAULT 0.0")
    private float reviewAverage;

    @Column(name = "review_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int reviewCount;

    @Column(name = "max_participant", nullable = false, columnDefinition = "INT DEFAULT 1")
    private int maxParticipant;

    @Column(name = "is_removed", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isRemoved;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TourCategoryEntity> tourCategories;

    @OneToMany(mappedBy = "tourId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlanEntity> plans;

    @OneToMany(mappedBy="tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishlistEntity> wishLists = new ArrayList<>();

    public static TourEntity toSaveEntity(TourRequestDTO tourRequestDTO){
        TourEntity tourEntity = new TourEntity();
        tourEntity.setTitle(tourRequestDTO.getTitle());
        tourEntity.setDescription(tourRequestDTO.getDescription());
        tourEntity.setLocation(tourRequestDTO.getLocation());
        tourEntity.setPrice(tourRequestDTO.getPrice());
        tourEntity.setStartDate(tourRequestDTO.getStartDate());
        tourEntity.setEndDate(tourRequestDTO.getEndDate());
        tourEntity.setReviewAverage(0.0F);
        tourEntity.setReviewCount(0);
        tourEntity.setMaxParticipant(tourRequestDTO.getMaxParticipants());
        tourEntity.setRemoved(false);
        return tourEntity;
    }
}

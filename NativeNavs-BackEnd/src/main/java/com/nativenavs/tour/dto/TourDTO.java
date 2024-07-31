package com.nativenavs.tour.dto;

import com.nativenavs.tour.entity.TourEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TourDTO {
    private int id;
    private int userId;
    private String title;
    private String thumbnailImage;
    private String description;
    private String location;
    private int price;
    private LocalDate startDate;
    private LocalDate endDate;
    private float reviewAverage;
    private int reviewCount;
    private int maxParticipants;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isRemoved;


    public static TourDTO toTourDTO(TourEntity tourEntity){
        TourDTO tourDTO = new TourDTO();
        tourDTO.setId(tourEntity.getId());
        tourDTO.setUserId(tourEntity.getUserId());
        tourDTO.setTitle(tourEntity.getTitle());
        tourDTO.setThumbnailImage(tourEntity.getThumbnailImage());
        tourDTO.setDescription(tourEntity.getDescription());
        tourDTO.setLocation(tourEntity.getLocation());
        tourDTO.setPrice(tourEntity.getPrice());
        tourDTO.setStartDate(tourEntity.getStartDate());
        tourDTO.setEndDate(tourEntity.getEndDate());
        tourDTO.setReviewAverage(tourEntity.getReviewAverage());
        tourDTO.setReviewCount(tourEntity.getReviewCount());
        tourDTO.setCreatedAt(tourEntity.getCreatedAt());
        tourDTO.setUpdatedAt(tourEntity.getUpdatedAt());
        tourDTO.setMaxParticipants(tourEntity.getMaxParticipant());
        tourDTO.setRemoved(tourEntity.isRemoved());
        return tourDTO;
    }
}

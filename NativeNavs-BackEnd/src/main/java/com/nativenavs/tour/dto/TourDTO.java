package com.nativenavs.tour.dto;

import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TourDTO {
    private int id;
    private UserDTO user;
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

    private List<Integer> categoryIds;
    private List<PlanDTO> plans;

    public static TourDTO toTourDTO(TourEntity tourEntity){
        TourDTO tourDTO = new TourDTO();
        tourDTO.setId(tourEntity.getId());
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

        if (tourEntity.getUser() != null) {
            UserDTO userDTO = UserDTO.toUserDTO(tourEntity.getUser()); // UserDTO 변환 메서드 호출
            tourDTO.setUser(userDTO);
        }

        List<PlanDTO> planDTOs = tourEntity.getPlans().stream()
                .map(plan -> new PlanDTO(
                        plan.getId(),
                        plan.getField(),
                        plan.getDescription(),
                        plan.getImage(),
                        plan.getLatitude(),
                        plan.getLongitude(),
                        plan.getAddressFull()))
                .collect(Collectors.toList());
        tourDTO.setPlans(planDTOs);

        return tourDTO;
    }
}

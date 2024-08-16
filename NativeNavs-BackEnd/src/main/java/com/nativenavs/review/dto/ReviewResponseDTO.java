package com.nativenavs.review.dto;

import com.nativenavs.review.entity.ReviewEntity;
import com.nativenavs.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.nativenavs.review.entity.ReviewImageEntity;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private int id;
    private int score;
    private String description;
    private LocalDateTime createdAt;
    private UserDTO reviewer;
    private List<String> imageUrls;
    private String tourTitle;


    public static ReviewResponseDTO toReviewDTO(ReviewEntity reviewEntity) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(reviewEntity.getId());
        dto.setScore(reviewEntity.getScore());
        dto.setDescription(reviewEntity.getDescription());
        dto.setCreatedAt(reviewEntity.getCreatedAt());
        dto.setReviewer(UserDTO.toUserDTO(reviewEntity.getReviewer()));
        dto.setTourTitle(reviewEntity.getTour().getTitle());
        dto.setImageUrls(reviewEntity.getImages().stream()
                .map(ReviewImageEntity::getImage)
                .collect(Collectors.toList()));
        return dto;
    }
}

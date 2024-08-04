package com.nativenavs.review.dto;

import com.nativenavs.review.entity.ReviewEntity;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
//    private int id;
//    private int score;
//    private String description;
//    private LocalDateTime createdAt;
//    private float
//    private UserDTO reviewer; // 리뷰어 정보
//    private int guideId; // 가이드 정보
//    private List<String> imageUrls; // 이미지 URL 리스트
//    private String tourTitle; // 투어 정보
//
//
//    public static ReviewResponseDTO toReviewDTO(ReviewEntity reviewEntity) {
//        ReviewResponseDTO dto = new ReviewResponseDTO();
//        dto.setId(reviewEntity.getId());
//        dto.setScore(reviewEntity.getScore());
//        dto.setDescription(reviewEntity.getDescription());
//        dto.setCreatedAt(reviewEntity.getCreatedAt());
//        dto.setReviewer(UserDTO.toUserDTO(reviewEntity.getReviewer()));
//        dto.setGuideId(reviewEntity.getGuide().getId());
//        dto.setTourTitle(reviewEntity.getTour().getTitle());
//
//        dto.setImageUrls(reviewEntity.getImages().stream()
//                .map(ReviewImageEntity::getImage)
//                .collect(Collectors.toList()));
//        return dto;
//    }
}

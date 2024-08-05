package com.nativenavs.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuideReviewDTO {
    private float reviewAverage;
    private int reviewCount;
    private int totalImageCount;
    private List<String> imageUrls;
    private List<ReviewResponseDTO> reviews;
}

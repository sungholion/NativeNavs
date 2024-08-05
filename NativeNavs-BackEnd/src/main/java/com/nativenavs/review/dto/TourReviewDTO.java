package com.nativenavs.review.dto;

import com.nativenavs.tour.dto.TourDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TourReviewDTO {
    private float reviewAverage;
    private List<String> imageUrls;
    private List<ReviewResponseDTO> reviews;
    // Getters and Setters
}

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
public class TravReviewDTO {
    private int reviewCount;
    private List<ReviewResponseDTO> reviews;
}

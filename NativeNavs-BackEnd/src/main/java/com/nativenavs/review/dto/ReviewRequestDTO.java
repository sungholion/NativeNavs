package com.nativenavs.review.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;





@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {
    private int tourId;
    private int score;
    private String description;
    private List<String> imageUrls;
    // Getters and Setters
}

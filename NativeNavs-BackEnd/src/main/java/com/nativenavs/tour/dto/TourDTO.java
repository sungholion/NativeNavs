package com.nativenavs.tour.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
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

}

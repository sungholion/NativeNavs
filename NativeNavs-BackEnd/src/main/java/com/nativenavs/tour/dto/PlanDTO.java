package com.nativenavs.tour.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {
    private int id;
    private String field;         // 일정을 설명할 필드
    private String description;  // 일정 설명
    private String image;        // 일정 사진
    private double latitude;     // 위도
    private double longitude;    // 경도
    private String addressFull;  // 주소
}
package com.nativenavs.tour.dto;

import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal latitude;     // 위도
    private BigDecimal longitude;    // 경도
    private String addressFull;  // 주소
}
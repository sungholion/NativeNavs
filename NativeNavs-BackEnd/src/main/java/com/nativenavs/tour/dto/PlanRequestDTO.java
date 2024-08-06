package com.nativenavs.tour.dto;

import jakarta.persistence.SequenceGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDTO {
//    private int id;
    private String field;         // 일정을 설명할 필드
    private String description;  // 일정 설명
    private MultipartFile image;        // 일정 사진
    private BigDecimal latitude;     // 위도
    private BigDecimal longitude;    // 경도
    private String addressFull;
}

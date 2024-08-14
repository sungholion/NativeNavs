package com.nativenavs.tour.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {
    private int id;
    private String field;
    private String description;
    private String image;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String addressFull;
}
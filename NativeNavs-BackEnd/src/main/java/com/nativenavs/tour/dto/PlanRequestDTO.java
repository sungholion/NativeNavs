package com.nativenavs.tour.dto;

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
    private int id;
    private String field;
    private String description;
    private String image;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String addressFull;
}

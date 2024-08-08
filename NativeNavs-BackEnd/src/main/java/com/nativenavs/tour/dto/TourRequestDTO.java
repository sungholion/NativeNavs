package com.nativenavs.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourRequestDTO {
    private String title;
    private String thumbnailImage;
    private String description;
    private String location;
    private int price;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxParticipants;

    private List<Integer> categoryIds;
    private List<PlanRequestDTO> plans;

}




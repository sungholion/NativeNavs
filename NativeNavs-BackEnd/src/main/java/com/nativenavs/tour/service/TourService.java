package com.nativenavs.tour.service;

import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    public void addTour(TourDTO tourDTO){
        TourEntity tourEntity = TourEntity.toSaveEntity(tourDTO);
        tourRepository.save(tourEntity);
    }
}

package com.nativenavs.tour.service;

import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //entity -> DTO 작업이 필요
    public List<TourDTO> findAllTours(){
        List<TourEntity> tourEntityList = tourRepository.findAll();
        List<TourDTO> tourDTOList = new ArrayList<>();
        for (TourEntity tourEntity: tourEntityList){
            tourDTOList.add(TourDTO.toTourDTO(tourEntity));
        }
        return tourDTOList;
    }


    public TourDTO findTourById(int id){
        Optional<TourEntity> optionalTourEntity = tourRepository.findById(id);
        if(optionalTourEntity.isPresent()){
            TourEntity tourEntity = optionalTourEntity.get();
            return TourDTO.toTourDTO(tourEntity);
        }else{
            return null;
        }
    }

    public void modifyTour(int id, TourDTO tourDTO){
        Optional<TourEntity> optionalTourEntity = tourRepository.findById(id);
        if(optionalTourEntity.isPresent()){
            TourEntity tourEntity = optionalTourEntity.get();
            tourEntity.setTitle(tourDTO.getTitle());
            tourEntity.setThumbnailImage(tourDTO.getThumbnailImage());
            tourEntity.setDescription(tourDTO.getDescription());
            tourEntity.setLocation(tourDTO.getLocation());
            tourEntity.setPrice(tourDTO.getPrice());
            tourEntity.setStartDate(tourDTO.getStartDate());
            tourEntity.setEndDate(tourDTO.getEndDate());
            tourEntity.setReviewAverage(tourDTO.getReviewAverage());
            tourEntity.setReviewCount(tourDTO.getReviewCount());
            tourEntity.setMaxParticipant(tourDTO.getMaxParticipants());
            tourRepository.save(tourEntity);
        }
    }

    public void removeTour(int id){
        tourRepository.deleteById(id);
    }
}

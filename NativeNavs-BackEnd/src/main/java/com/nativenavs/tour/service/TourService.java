package com.nativenavs.tour.service;

import com.nativenavs.tour.dto.PlanDTO;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.entity.CategoryEntity;
import com.nativenavs.tour.entity.PlanEntity;
import com.nativenavs.tour.entity.TourCategoryEntity;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.CategoryRepository;
import com.nativenavs.tour.repository.PlanRepository;
import com.nativenavs.tour.repository.TourCategoryRepository;
import com.nativenavs.tour.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class TourService {
    @Autowired
    private final TourRepository tourRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TourCategoryRepository tourCategoryRepository;
    @Autowired
    private PlanRepository planRepository;

    public void addTour(TourDTO tourDTO){
        TourEntity tourEntity = TourEntity.toSaveEntity(tourDTO);
        TourEntity savedTour = tourRepository.save(tourEntity);

        List<Integer> categoryIds = tourDTO.getCategoryIds();
        // 카테고리 정보 처리
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Integer categoryId : categoryIds) {
                CategoryEntity category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));

                TourCategoryEntity tourCategoryEntity = new TourCategoryEntity();
                tourCategoryEntity.setTour(savedTour);
                tourCategoryEntity.setCategory(category);
                tourCategoryRepository.save(tourCategoryEntity);
            }
        }
        // 일정 정보 처리
        List<PlanDTO> planDTOs = tourDTO.getPlans();
        if (planDTOs != null && !planDTOs.isEmpty()) {
            for (PlanDTO planDTO : planDTOs) {
                PlanEntity planEntity = new PlanEntity();
                planEntity.setTourId(savedTour);
                planEntity.setField(planDTO.getField());
                planEntity.setDescription(planDTO.getDescription());
                planEntity.setImage(planDTO.getImage());
                planEntity.setLatitude(planDTO.getLatitude());
                planEntity.setLongitude(planDTO.getLongitude());
                planEntity.setAddressFull(planDTO.getAddressFull());
                planRepository.save(planEntity);
            }
        }

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
            TourDTO tourDTO = TourDTO.toTourDTO(tourEntity);

            // Fetching categories
            List<Integer> categoryIds = tourEntity.getTourCategories().stream()
                    .map(tc -> tc.getCategory().getId())
                    .collect(Collectors.toList());
            tourDTO.setCategoryIds(categoryIds);

            // Fetching plans
            List<PlanDTO> planDTOs = tourEntity.getPlans().stream()
                    .map(plan -> new PlanDTO(
                            plan.getId(),
                            plan.getField(),
                            plan.getDescription(),
                            plan.getImage(),
                            plan.getLatitude(),
                            plan.getLongitude(),
                            plan.getAddressFull()))
                    .collect(Collectors.toList());
            tourDTO.setPlans(planDTOs);

            return tourDTO;
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

package com.nativenavs.tour.service;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.tour.dto.CategoryDTO;
import com.nativenavs.tour.dto.PlanDTO;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.entity.CategoryEntity;
import com.nativenavs.tour.entity.PlanEntity;
import com.nativenavs.tour.entity.TourCategoryEntity;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.*;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import com.nativenavs.user.service.UserService;
import com.nativenavs.user.service.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public void addTour(TourDTO tourDTO, String token){
        TourEntity tourEntity = TourEntity.toSaveEntity(tourDTO);

        //이메일로 id 반환
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        int userIdFromEmail = userService.changeEmailToId(email);
        tourEntity.setUserId(userIdFromEmail);

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
            UserEntity userEntity = userRepository.findById(tourEntity.getUserId()).orElseThrow(()-> new NoSuchElementException("User not found"));
            UserDTO userDTO = UserDTO.toUserDTO(userEntity);
            TourDTO tourDTO = TourDTO.toTourDTO(tourEntity,userDTO);
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

            tourDTOList.add(tourDTO);
        }
        return tourDTOList;
    }


    public TourDTO findTourById(int id){
        Optional<TourEntity> optionalTourEntity = tourRepository.findById(id);
        if(optionalTourEntity.isPresent()){
            TourEntity tourEntity = optionalTourEntity.get();

            UserEntity userEntity = userRepository.findById(tourEntity.getUserId()).orElseThrow(()-> new NoSuchElementException("User not found"));
            UserDTO userDTO = UserDTO.toUserDTO(userEntity);
            TourDTO tourDTO = TourDTO.toTourDTO(tourEntity,userDTO);

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

    @Transactional
    public void modifyTour(int id, TourDTO tourDTO){
        Optional<TourEntity> optionalTourEntity = tourRepository.findById(id);
        if(optionalTourEntity.isPresent()){
            TourEntity tourEntity = optionalTourEntity.get();
            updateTourEntityFields(tourEntity, tourDTO);
            updateTourCategories(tourEntity, tourDTO.getCategoryIds());
            updateTourPlans(tourEntity, tourDTO.getPlans());
            tourRepository.save(tourEntity);
        }
    }

    private void updateTourEntityFields(TourEntity tourEntity, TourDTO tourDTO) {
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
    }

    private void updateTourCategories(TourEntity tourEntity, List<Integer> categoryIds) {
        // 기존 카테고리 삭제
        tourCategoryRepository.deleteByTourId(tourEntity.getId());

        // 새로운 카테고리 추가
        if (categoryIds != null) {
            for (Integer categoryId : categoryIds) {
                Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
                if (categoryEntity.isPresent()) {
                    TourCategoryEntity tourCategoryEntity = new TourCategoryEntity();
                    tourCategoryEntity.setTour(tourEntity);
                    tourCategoryEntity.setCategory(categoryEntity.get());
                    tourCategoryRepository.save(tourCategoryEntity);
                }
            }
        }
    }

    private void updateTourPlans(TourEntity tourEntity, List<PlanDTO> plans) {
        // 기존 플랜 삭제
        planRepository.deleteByTourId(tourEntity.getId());
        // 새로운 플랜 추가
        if (plans != null) {
            for (PlanDTO planDTO : plans) {
                PlanEntity planEntity = new PlanEntity();
                planEntity.setTourId(tourEntity);
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

    public void removeTour(int id){
        tourRepository.deleteById(id);
    }

    public List<TourDTO> searchTours(String location, LocalDate date, Integer categoryId) {
        Specification<TourEntity> spec = Specification.where(null);

        if (location != null && !location.isEmpty()) {
            spec = spec.and(TourSpecification.hasLocationContaining(location));
        }
        if (date != null) {
            spec = spec.and(TourSpecification.isDateInRange(date));
        }
        if (categoryId != null) {
            spec = spec.and(TourSpecification.hasCategory(categoryId));
        }
        List<TourEntity> tourEntities = tourRepository.findAll(spec);
        return tourEntities.stream()
                .map(tourEntity->{
                    UserEntity userEntity = userRepository.findById(tourEntity.getUserId())
                            .orElseThrow(() -> new NoSuchElementException("User not found"));
                    // UserDTO로 변환
                    UserDTO userDTO = UserDTO.toUserDTO(userEntity);
                    // TourDTO로 변환 및 유저 정보 추가
                    return TourDTO.toTourDTO(tourEntity, userDTO);
                })
                .collect(Collectors.toList());
    }

}

package com.nativenavs.tour.service;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.common.service.AwsS3ObjectStorage;
import com.nativenavs.tour.dto.*;
import com.nativenavs.reservation.repository.ReservationRepository;
import com.nativenavs.reservation.service.ReservationService;
import com.nativenavs.tour.dto.CategoryDTO;
import com.nativenavs.tour.dto.GuideTourDTO;
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
import com.nativenavs.wishlist.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private AwsS3ObjectStorage awsS3ObjectStorageUpload;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;


    public void addTour(TourRequestDTO tourRequestDTO, int userId, MultipartFile thumbnailImage, List<MultipartFile> planImages) {
        TourEntity tourEntity = TourEntity.toSaveEntity(tourRequestDTO);
        // 썸네일 이미지
        String thumbnailUrl = awsS3ObjectStorageUpload.uploadFile(thumbnailImage);
        tourEntity.setThumbnailImage(thumbnailUrl);
        tourEntity.setUserId(userId);

        TourEntity savedTour = tourRepository.save(tourEntity);

        // 카테고리 정보 처리
        List<Integer> categoryIds = tourRequestDTO.getCategoryIds();
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

        List<String> planImageUrls = new ArrayList<>();
        for (MultipartFile planImage : planImages) {
            String s = awsS3ObjectStorageUpload.uploadFile(planImage);
            planImageUrls.add(s);
        }
        for(int i=0;i<tourRequestDTO.getPlans().size();i++){
            tourRequestDTO.getPlans().get(i).setImage(planImageUrls.get(i));
        }
        // 일정 정보 처리
        List<PlanRequestDTO> planRequestDTOS = tourRequestDTO.getPlans();
        if (planRequestDTOS != null && !planRequestDTOS.isEmpty()) {
            for (PlanRequestDTO planRequestDTO: planRequestDTOS) {
                PlanEntity planEntity = new PlanEntity();
                planEntity.setTourId(savedTour);
                planEntity.setField(planRequestDTO.getField());
                planEntity.setDescription(planRequestDTO.getDescription());
                planEntity.setImage(planRequestDTO.getImage());
                planEntity.setLatitude(planRequestDTO.getLatitude());
                planEntity.setLongitude(planRequestDTO.getLongitude());
                planEntity.setAddressFull(planRequestDTO.getAddressFull());
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
    public void modifyTour(int id, TourRequestDTO tourRequestDTO,MultipartFile thumbnailImage, List<MultipartFile> planImages) {
        Optional<TourEntity> optionalTourEntity = tourRepository.findById(id);
        if(optionalTourEntity.isPresent()){
            TourEntity tourEntity = optionalTourEntity.get();

            updateTourEntityFields(tourEntity, tourRequestDTO, thumbnailImage);
            updateTourCategories(tourEntity, tourRequestDTO.getCategoryIds());
            updateTourPlans(tourEntity, tourRequestDTO.getPlans(), planImages);

            tourRepository.save(tourEntity);
        }
    }

    private void updateTourEntityFields(TourEntity tourEntity, TourRequestDTO tourRequestDTO, MultipartFile thumbnailImage) {
        tourEntity.setTitle(tourRequestDTO.getTitle());

        if(tourEntity.getThumbnailImage() != null && !thumbnailImage.isEmpty()){
            if (tourEntity.getThumbnailImage() != null) {
                awsS3ObjectStorageUpload.deleteFile(tourEntity.getThumbnailImage());
            }
        }
        String thumbnailUrl = awsS3ObjectStorageUpload.uploadFile(thumbnailImage);
        tourEntity.setThumbnailImage(thumbnailUrl);

        tourEntity.setDescription(tourRequestDTO.getDescription());
        tourEntity.setLocation(tourRequestDTO.getLocation());
        tourEntity.setPrice(tourRequestDTO.getPrice());
        tourEntity.setStartDate(tourRequestDTO.getStartDate());
        tourEntity.setEndDate(tourRequestDTO.getEndDate());
        tourEntity.setMaxParticipant(tourRequestDTO.getMaxParticipants());
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

    private void updateTourPlans(TourEntity tourEntity, List<PlanRequestDTO> plans, List<MultipartFile> planImages) {
        List<PlanEntity> currentPlans = planRepository.findByTourId(tourEntity.getId());
        int imageIndex = 0;

        for (int i = 0; i < plans.size(); i++) {
            PlanRequestDTO planDTO = plans.get(i);
            PlanEntity planEntity = null;

            // 기존 플랜 수정 또는 새로운 플랜 추가
            if (i < currentPlans.size()) {
                planEntity = currentPlans.get(i);
            } else {
                planEntity = new PlanEntity();
                planEntity.setTourId(tourEntity);
            }

            planEntity.setField(planDTO.getField());
            planEntity.setDescription(planDTO.getDescription());

            // 이미지가 제공된 경우에만 처리
            if (planImages != null && imageIndex < planImages.size() && planImages.get(imageIndex) != null) {
                MultipartFile image = planImages.get(imageIndex);
                if (!image.isEmpty()) {
                    if (planEntity.getImage() != null) {
                        awsS3ObjectStorageUpload.deleteFile(planEntity.getImage());
                    }
                    String imageUrl = awsS3ObjectStorageUpload.uploadFile(image);
                    planEntity.setImage(imageUrl);
                }
                imageIndex++;
            }

            planEntity.setLatitude(planDTO.getLatitude());
            planEntity.setLongitude(planDTO.getLongitude());
            planEntity.setAddressFull(planDTO.getAddressFull());

            planRepository.save(planEntity);
        }

        // 남은 기존 플랜 삭제
        if (plans.size() < currentPlans.size()) {
            for (int i = plans.size(); i < currentPlans.size(); i++) {
                planRepository.delete(currentPlans.get(i));
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

    public List<GuideTourDTO> findToursByGuide(int guideId) {
        List<TourEntity> tours = tourRepository.findByUserId(guideId);
        return tours.stream().map(this::convertToGuideTourDTO).collect(Collectors.toList());
    }


    private GuideTourDTO convertToGuideTourDTO(TourEntity tour) {
        GuideTourDTO dto = new GuideTourDTO();
        dto.setTourId(tour.getId());
        dto.setThumbnailImage(tour.getThumbnailImage());
        dto.setReservationCount(reservationRepository.countByTour(tour));
        dto.setWishedCount(wishlistRepository.countByTourId(tour.getId()));
        dto.setTitle(tour.getTitle());
        dto.setRemoved(tour.isRemoved());
        dto.setStartDate(tour.getStartDate());
        dto.setEndDate(tour.getEndDate());
        return dto;
    }

}

package com.nativenavs.tour.service;

import com.nativenavs.common.service.AwsS3ObjectStorage;
import com.nativenavs.reservation.repository.ReservationRepository;
import com.nativenavs.reservation.service.ReservationService;
import com.nativenavs.tour.dto.*;
import com.nativenavs.tour.entity.CategoryEntity;
import com.nativenavs.tour.entity.PlanEntity;
import com.nativenavs.tour.entity.TourCategoryEntity;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.*;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import com.nativenavs.user.service.UserService;
import com.nativenavs.wishlist.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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


    public int addTour(TourRequestDTO tourRequestDTO, int userId, MultipartFile thumbnailImage, List<MultipartFile> planImages) {
        TourEntity tourEntity = TourEntity.toSaveEntity(tourRequestDTO);
        // 썸네일 이미지
        String thumbnailUrl = awsS3ObjectStorageUpload.uploadFile(thumbnailImage);
        tourEntity.setThumbnailImage(thumbnailUrl);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // UserEntity를 TourEntity에 설정
        tourEntity.setUser(userEntity);

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
        for (int i = 0; i < tourRequestDTO.getPlans().size(); i++) {
            tourRequestDTO.getPlans().get(i).setImage(planImageUrls.get(i));
        }
        // 일정 정보 처리
        List<PlanRequestDTO> planRequestDTOS = tourRequestDTO.getPlans();
        if (planRequestDTOS != null && !planRequestDTOS.isEmpty()) {
            for (PlanRequestDTO planRequestDTO : planRequestDTOS) {
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

        return savedTour.getId();

    }

    //entity -> DTO 작업이 필요
    public List<TourDTO> findAllTours() {
        List<TourEntity> tourEntityList = tourRepository.findAll().stream()
                .sorted(Comparator.comparingInt(TourEntity::getReviewCount).reversed())
                .toList();


        List<TourDTO> tourDTOList = new ArrayList<>();

        for (TourEntity tourEntity : tourEntityList) {
            TourDTO tourDTO = TourDTO.toTourDTO(tourEntity);
//            List<PlanDTO> planDTOs = tourEntity.getPlans().stream()
//                    .map(plan -> new PlanDTO(
//                            plan.getId(),
//                            plan.getField(),
//                            plan.getDescription(),
//                            plan.getImage(),
//                            plan.getLatitude(),
//                            plan.getLongitude(),
//                            plan.getAddressFull()))
//                    .collect(Collectors.toList());
//            tourDTO.setPlans(planDTOs);

            tourDTOList.add(tourDTO);
        }
        return tourDTOList;
    }


    public TourDTO findTourById(int id) {
        Optional<TourEntity> optionalTourEntity = tourRepository.findById(id);
        if (optionalTourEntity.isPresent()) {
            TourEntity tourEntity = optionalTourEntity.get();
            TourDTO tourDTO = TourDTO.toTourDTO(tourEntity);

            // Fetching categories
            List<Integer> categoryIds = tourEntity.getTourCategories().stream()
                    .map(tc -> tc.getCategory().getId())
                    .collect(Collectors.toList());
            tourDTO.setCategoryIds(categoryIds);

//            // Fetching plans
//            List<PlanDTO> planDTOs = tourEntity.getPlans().stream()
//                    .map(plan -> new PlanDTO(
//                            plan.getId(),
//                            plan.getField(),
//                            plan.getDescription(),
//                            plan.getImage(),
//                            plan.getLatitude(),
//                            plan.getLongitude(),
//                            plan.getAddressFull()))
//                    .collect(Collectors.toList());
//            tourDTO.setPlans(planDTOs);

            return tourDTO;
        } else {
            return null;
        }
    }

    @Transactional
    public void modifyTour(int id, TourRequestDTO tourRequestDTO, MultipartFile thumbnailImage, List<MultipartFile> planImages) {
        Optional<TourEntity> optionalTourEntity = tourRepository.findById(id);
        if (optionalTourEntity.isPresent()) {
            TourEntity tourEntity = optionalTourEntity.get();

            updateTourEntityFields(tourEntity, tourRequestDTO, thumbnailImage);
            updateTourCategories(tourEntity, tourRequestDTO.getCategoryIds());
            updateTourPlans(tourEntity, tourRequestDTO.getPlans(), planImages);

            tourRepository.save(tourEntity);
        }
    }

    private void updateTourEntityFields(TourEntity tourEntity, TourRequestDTO tourRequestDTO, MultipartFile thumbnailImage) {
        tourEntity.setTitle(tourRequestDTO.getTitle());
        System.out.println("kjasdfh;lasdfh;lkdahfl;aldsf");
        if (tourRequestDTO.getThumbnailImage().equals("")) {
            awsS3ObjectStorageUpload.deleteFile(tourEntity.getThumbnailImage());
            String thumbnailUrl = awsS3ObjectStorageUpload.uploadFile(thumbnailImage);
            tourEntity.setThumbnailImage(thumbnailUrl);
        }
        System.out.println("이까지오면 이로직은 문제가 없다");
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
        // 기존 플랜 가져오기
        List<PlanEntity> currentPlans = planRepository.findByTourId(tourEntity.getId());

        // 기존 플랜을 모두 삭제
        planRepository.deleteAll(currentPlans);

        int imageIndex = 0;

        // 새로운 플랜 저장
        for (PlanRequestDTO planDTO : plans) {
            PlanEntity planEntity = new PlanEntity();
            planEntity.setTourId(tourEntity);
            planEntity.setField(planDTO.getField());
            planEntity.setDescription(planDTO.getDescription());
            planEntity.setLatitude(planDTO.getLatitude());
            planEntity.setLongitude(planDTO.getLongitude());
            planEntity.setAddressFull(planDTO.getAddressFull());

            // 플랜 이미지 처리
            if (planDTO.getImage() != null && !planDTO.getImage().isEmpty()) {
                // 기존 플랜의 이미지가 있는 경우 그대로 사용
                planEntity.setImage(planDTO.getImage());
            } else if (planImages != null && imageIndex < planImages.size() && planImages.get(imageIndex) != null && !planImages.get(imageIndex).isEmpty()) {
                // 새 플랜에 대한 이미지 업로드 처리
                String imageUrl = awsS3ObjectStorageUpload.uploadFile(planImages.get(imageIndex));
                planEntity.setImage(imageUrl);
                imageIndex++;
            }

            // 플랜 엔티티 저장
            planRepository.save(planEntity);
        }
    }


    public void removeTour(int id) {

        tourRepository.deleteById(id);
    }

    public List<TourDTO> searchTours(String location, LocalDate date, List<Integer> categoryIds) {
        Specification<TourEntity> spec = Specification.where(null);

        if (location != null && !location.isEmpty()) {
            spec = spec.and(TourSpecification.hasLocationContaining(location));
        }
        if (date != null) {
            spec = spec.and(TourSpecification.isDateInRange(date));
        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and(TourSpecification.hasCategory(categoryIds));
        }
        List<TourEntity> tourEntities = tourRepository.findAll(spec);
        return tourEntities.stream()
                .map(TourDTO::toTourDTO)
                .toList();
    }

    public List<GuideTourDTO> findToursByGuide(int guideId) {
        List<TourEntity> tours = tourRepository.findByUserId(guideId);

        return tours.stream().map(this::convertToGuideTourDTO).collect(Collectors.toList());

    }


    public GuideTourDTO convertToGuideTourDTO(TourEntity tour) {
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

    public TourDTO findTourByTourId(int tourId) {
        Optional<TourEntity> tourEntity = tourRepository.findById(tourId);
        return tourEntity.map(TourDTO::toTourDTO).orElse(null);

    }
}

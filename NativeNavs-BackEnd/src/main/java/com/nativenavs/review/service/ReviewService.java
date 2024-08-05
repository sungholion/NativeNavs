package com.nativenavs.review.service;

import com.nativenavs.review.dto.*;
import com.nativenavs.review.entity.ReviewEntity;
import com.nativenavs.review.entity.ReviewImageEntity;
import com.nativenavs.review.repository.ReviewImageRepository;
import com.nativenavs.review.repository.ReviewRepository;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TourRepository tourRepository;

    @Transactional
    public ReviewEntity addReview(ReviewRequestDTO reviewRequestDTO, UserEntity reviewer) {
        // Tour, Guide와 Reviewer 정보를 조회
        TourEntity tour = tourRepository.findById(reviewRequestDTO.getTourId())
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        //투어 정보에 해당하는 guidId 찾기
        UserEntity guide = userRepository.findById(tour.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Guide not found"));
        // 리뷰 생성
        ReviewEntity review = new ReviewEntity();
        review.setReviewer(reviewer);
        review.setTour(tour);
        review.setGuide(guide);
        review.setScore(reviewRequestDTO.getScore());
        review.setDescription(reviewRequestDTO.getDescription());
        review.setCreatedAt(LocalDateTime.now());
        review = reviewRepository.save(review);

        // 리뷰와 관련된 이미지 저장
        if (reviewRequestDTO.getImageUrls() != null) {
            for (String imageUrl : reviewRequestDTO.getImageUrls()) {
                ReviewImageEntity reviewImage = new ReviewImageEntity();
                reviewImage.setReview(review);
                reviewImage.setImage(imageUrl);
                reviewImageRepository.save(reviewImage);
            }
        }
        return review;
    }

    public TourReviewDTO findReviewByTourId(int tourId){
        TourEntity tour = tourRepository.findById(tourId).orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        List<ReviewEntity> reviewEntities = reviewRepository.findByTourId(tourId);
        List<String> imageUrls = reviewEntities.stream().flatMap(review -> review.getImages().stream())
                .map(ReviewImageEntity::getImage).collect(Collectors.toList());

        List<ReviewResponseDTO> reviewDTOs = reviewEntities.stream()
                .map(ReviewResponseDTO::toReviewDTO)
                .collect(Collectors.toList());

        // 최종 반환 DTO 생성 및 데이터 설정
        TourReviewDTO responseDTO = new TourReviewDTO();
        responseDTO.setReviewAverage(tour.getReviewAverage());
        responseDTO.setImageUrls(imageUrls);
        responseDTO.setReviews(reviewDTOs);
        responseDTO.setReviewCount(tour.getReviewCount());
        responseDTO.setTotalImageCount(imageUrls.size()); // 5:

        return responseDTO;
    }

    public GuideReviewDTO findReviewByGuideId(int guideId){
        UserEntity guide = userRepository.findById(guideId).orElseThrow(() -> new IllegalArgumentException("guide not found"));

        List<ReviewEntity> reviewEntities = reviewRepository.findByGuideId(guideId);
        List<String> imageUrls = reviewEntities.stream().flatMap(review -> review.getImages().stream())
                .map(ReviewImageEntity::getImage).collect(Collectors.toList());

        List<ReviewResponseDTO> reviewDTOs = reviewEntities.stream()
                .map(ReviewResponseDTO::toReviewDTO)
                .collect(Collectors.toList());

        // 최종 반환 DTO 생성 및 데이터 설정
        GuideReviewDTO responseDTO = new GuideReviewDTO();
        responseDTO.setReviewAverage(guide.getNavReviewAverage());
        responseDTO.setImageUrls(imageUrls);
        responseDTO.setReviews(reviewDTOs);
        responseDTO.setReviewCount(guide.getNavReviewCount());
        responseDTO.setTotalImageCount(imageUrls.size()); // 5:

        return responseDTO;
    }

    public TravReviewDTO findReviewByUserId(int userId){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("guide not found"));

        List<ReviewEntity> reviewEntities = reviewRepository.findByReviewerId(userId);

        List<ReviewResponseDTO> reviewDTOs = reviewEntities.stream()
                .map(ReviewResponseDTO::toReviewDTO)
                .collect(Collectors.toList());

        // 최종 반환 DTO 생성 및 데이터 설정
        TravReviewDTO responseDTO = new TravReviewDTO();
        responseDTO.setReviews(reviewDTOs);
        responseDTO.setReviewCount(reviewDTOs.size());

        return responseDTO;
    }

}

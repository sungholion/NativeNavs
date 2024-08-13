package com.nativenavs.review.service;

import com.nativenavs.common.service.AwsS3ObjectStorage;
import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.repository.ReservationRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewImageRepository reviewImageRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;
    private final AwsS3ObjectStorage awsS3ObjectStorageUpload;


    @Transactional
    public ReviewEntity addReview(ReviewRequestDTO reviewRequestDTO, UserEntity reviewer,List<MultipartFile> images, int reservationId) {
        // Tour, Guide와 Reviewer 정보를 조회
        TourEntity tour = tourRepository.findById(reviewRequestDTO.getTourId())
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));
        //투어 정보에 해당하는 guidId 찾기
        UserEntity guide = tour.getUser();
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
        if (images != null) {
            for (MultipartFile image : images) {
                ReviewImageEntity reviewImage = new ReviewImageEntity();
                reviewImage.setReview(review);

                String url = awsS3ObjectStorageUpload.uploadFile(image);
                reviewImage.setImage(url);

                reviewImageRepository.save(reviewImage);
            }
        }

        updateTourReviewStats(tour, reviewRequestDTO.getScore());
        updateGuideReviewStats(guide, reviewRequestDTO.getScore());
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.setReviewed(true);
        reservationRepository.save(reservation);

        return review;
    }

    private void updateTourReviewStats(TourEntity tour, int newScore) {
        // 리뷰 수 증가
        int newReviewCount = tour.getReviewCount() + 1;
        // 새로운 평균 점수 계산
        float newAverageScore = ((tour.getReviewAverage() * tour.getReviewCount()) + newScore) / newReviewCount;
        // 업데이트된 값 설정
        tour.setReviewCount(newReviewCount);
        tour.setReviewAverage(newAverageScore);

        // 저장
        tourRepository.save(tour);
    }

    private void updateGuideReviewStats(UserEntity guide, int newScore) {
        // 리뷰 수 증가
        int newReviewCount = guide.getNavReviewCount() + 1;
        // 새로운 평균 점수 계산
        float newAverageScore = ((guide.getNavReviewAverage() * guide.getNavReviewCount()) + newScore) / newReviewCount;
        // 업데이트된 값 설정
        guide.setNavReviewCount(newReviewCount);
        guide.setNavReviewAverage(newAverageScore);

        // 저장
        userRepository.save(guide);
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

    public TravReviewDTO findReviewByUserId(int travId){
//        UserEntity trav = userRepository.findById(travId).orElseThrow(() -> new IllegalArgumentException("guide not found"));

        List<ReviewEntity> reviewEntities = reviewRepository.findByReviewerId(travId);

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

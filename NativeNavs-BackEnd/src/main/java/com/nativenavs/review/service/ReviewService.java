package com.nativenavs.review.service;

import com.nativenavs.review.dto.ReviewRequestDTO;
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

}

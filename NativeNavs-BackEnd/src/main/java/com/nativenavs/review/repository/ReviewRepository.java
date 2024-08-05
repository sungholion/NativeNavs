package com.nativenavs.review.repository;

import com.nativenavs.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> { // JpaRepository를 상속�{
    List<ReviewEntity> findByTourId(Integer tourId);
    List<ReviewEntity> findByGuideId(Integer guideId);
    List<ReviewEntity> findByReviewerId(Integer reviewerId);
}

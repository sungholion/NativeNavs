package com.nativenavs.review.repository;

import com.nativenavs.review.entity.ReviewEntity;
import com.nativenavs.review.entity.ReviewImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImageEntity, Integer> {

}

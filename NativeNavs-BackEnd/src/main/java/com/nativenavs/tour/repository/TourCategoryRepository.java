package com.nativenavs.tour.repository;

import com.nativenavs.tour.entity.TourCategoryEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourCategoryRepository extends JpaRepository<TourCategoryEntity, Integer> {
}

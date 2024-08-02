package com.nativenavs.tour.repository;

import com.nativenavs.tour.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TourRepository extends JpaRepository<TourEntity, Integer> {
}

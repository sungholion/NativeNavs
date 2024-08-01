package com.nativenavs.tour.repository;

import com.nativenavs.tour.entity.TourCategoryEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TourCategoryRepository extends JpaRepository<TourCategoryEntity, Integer> {

    @Modifying
    @Query("DELETE FROM TourCategoryEntity tce WHERE tce.tour.id = :tourId")
    void deleteByTourId(@Param("tourId") int tourId);
}

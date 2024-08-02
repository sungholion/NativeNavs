package com.nativenavs.tour.repository;

import com.nativenavs.tour.entity.PlanEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PlanRepository extends JpaRepository<PlanEntity,Integer> {
    @Modifying
    @Query("DELETE FROM PlanEntity p WHERE p.tourId.id = :tourId")
    void deleteByTourId(@Param("tourId") int tourId);
}

package com.nativenavs.tour.repository;

import com.nativenavs.tour.entity.PlanEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<PlanEntity,Integer> {


    @Query("SELECT p FROM PlanEntity p WHERE p.tourId.id = :tourId")
    List<PlanEntity> findByTourId(@Param("tourId") int tourId);

    @Modifying
    @Query("DELETE FROM PlanEntity p WHERE p.tourId.id = :tourId")
    void deleteByTourId(@Param("tourId") int tourId);
}

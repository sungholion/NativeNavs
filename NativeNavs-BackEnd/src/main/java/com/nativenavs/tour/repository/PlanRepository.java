package com.nativenavs.tour.repository;

import com.nativenavs.tour.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<PlanEntity,Integer> {
}

package com.nativenavs.tour.repository;

import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TourRepository extends JpaRepository<TourEntity, Integer>, JpaSpecificationExecutor<TourEntity> {

    List<TourEntity> findByUserId(int guideId);
}

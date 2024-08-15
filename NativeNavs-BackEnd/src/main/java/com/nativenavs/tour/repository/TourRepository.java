package com.nativenavs.tour.repository;

import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.enums.ReservationStatus;
import com.nativenavs.reservation.service.ReservationService;
import com.nativenavs.tour.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<TourEntity, Integer>, JpaSpecificationExecutor<TourEntity> {
    @Query("SELECT t FROM TourEntity t WHERE t.isRemoved = false")
    List<TourEntity> findAllActiveTours();

    List<TourEntity> findByUserId(int guideId);

    Optional<TourEntity> findByIdAndIsRemovedFalse(int id);

    List<TourEntity> findByUserIdAndIsRemovedFalse(int userId);

}

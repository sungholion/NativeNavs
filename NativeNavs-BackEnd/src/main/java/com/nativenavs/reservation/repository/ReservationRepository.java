package com.nativenavs.reservation.repository;

import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.enums.ReservationStatus;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {
    List<ReservationEntity> findByParticipantAndStatusOrderByCreatedAtDesc(UserEntity participant, ReservationStatus status);

    List<ReservationEntity> findByTourAndGuideAndStatus(TourEntity tourId, UserEntity guide, ReservationStatus status);
}

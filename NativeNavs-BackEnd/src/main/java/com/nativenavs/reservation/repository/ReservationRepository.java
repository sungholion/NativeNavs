package com.nativenavs.reservation.repository;

import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.enums.ReservationStatus;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {
    List<ReservationEntity> findByParticipantAndStatusOrderByCreatedAtDesc(UserEntity participant, ReservationStatus status);
    int countByTourAndStatusOrderByCreatedAtDesc(TourEntity tour,  ReservationStatus status);
    int countByTour(TourEntity tour);
    List<ReservationEntity> findByTourAndStatusOrderByDateAsc(TourEntity tourId, ReservationStatus status);
    Optional<ReservationEntity> findById(Integer id);


    @Query("SELECT COUNT(r) FROM ReservationEntity r WHERE r.participant.id = :participantId")
    int countByParticipantId(@Param("participantId") int participantId);



}

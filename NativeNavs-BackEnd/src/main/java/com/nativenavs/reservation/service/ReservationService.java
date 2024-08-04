package com.nativenavs.reservation.service;

import com.nativenavs.reservation.dto.ReservationDTO;
import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.enums.ReservationStatus;
import com.nativenavs.reservation.repository.ReservationRepository;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    public ReservationEntity addReservation(ReservationDTO requestDTO, int guideId) {
        UserEntity guide = userRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Guide not found"));

        TourEntity tour = tourRepository.findById(requestDTO.getTourId())
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        ReservationEntity reservation = new ReservationEntity();
        reservation.setGuide(guide);
        reservation.setTour(tour);
        reservation.setDate(requestDTO.getDate());
        reservation.setParticipantId(requestDTO.getParticipantId());
        reservation.setStartAt(requestDTO.getStartAt());
        reservation.setEndAt(requestDTO.getEndAt());
        reservation.setParticipantCount(requestDTO.getParticipantCount());
        reservation.setDescription(requestDTO.getDescription());
        reservation.setMeetingLatitude(requestDTO.getMeetingLatitude());
        reservation.setMeetingLongitude(requestDTO.getMeetingLongitude());
        reservation.setStatus(ReservationStatus.RESERVATION);
        reservation.setCreatedAt(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }
}

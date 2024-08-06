package com.nativenavs.reservation.service;

import com.nativenavs.reservation.dto.*;
import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.enums.ReservationStatus;
import com.nativenavs.reservation.repository.ReservationRepository;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    public ReservationEntity addReservation(ReservationRequestDTO requestDTO, int guideId) {
        UserEntity guide = userRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Guide not found"));

        UserEntity participant = userRepository.findById(requestDTO.getParticipantId())
                .orElseThrow(() -> new IllegalArgumentException("Participant not found"));

        TourEntity tour = tourRepository.findById(requestDTO.getTourId())
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        ReservationEntity reservation = new ReservationEntity();
        reservation.setGuide(guide);
        reservation.setTour(tour);
        reservation.setDate(requestDTO.getDate());
        reservation.setParticipant(participant);
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

    public ReservationResponseDTO getReservationDetails(int reservationId){
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservationId));

        return ReservationResponseDTO.toReservationDTO(reservationEntity);
    }


    public void removeReservation(int reservationId){
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservationId));

        if (reservationEntity.getStatus() == ReservationStatus.CANCEL) {
            throw new RuntimeException("Reservation is already canceled");
        }

        reservationEntity.setStatus(ReservationStatus.CANCEL);
        reservationRepository.save(reservationEntity);
    }


    public ReservationResponseDTOWrapper getReservationsForParticipant(UserEntity participant) {
        List<ReservationEntity> reservationsInProgress = reservationRepository.findByParticipantAndStatusOrderByCreatedAtDesc(participant, ReservationStatus.RESERVATION);
        List<ReservationEntity> reservationsCompleted = reservationRepository.findByParticipantAndStatusOrderByCreatedAtDesc(participant, ReservationStatus.DONE);

        List<ReservationResponseDTO> inProgressDTOs = reservationsInProgress.stream()
                .map(ReservationResponseDTO::toReservationDTO)
                .collect(Collectors.toList());

        List<ReservationResponseDTO> completedDTOs = reservationsCompleted.stream()
                .map(ReservationResponseDTO::toReservationDTO)
                .collect(Collectors.toList());

        return new ReservationResponseDTOWrapper(inProgressDTOs, completedDTOs);
    }

    public List<ReservationResponseDTO> getParticipantsForTour(TourEntity tour, UserEntity guide) {
        List<ReservationEntity> reservations=reservationRepository.findByTourAndGuideAndStatus(tour, guide, ReservationStatus.RESERVATION);
        return reservations.stream()
                .map(ReservationResponseDTO::toReservationDTO)
                .collect(Collectors.toList());
    }



    public void finishReservation(int reservationId) {
        // 예약을 조회합니다.
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약을 찾을 수 없습니다: " + reservationId));
        // 상태를 DONE으로 변경합니다.
        reservation.setStatus(ReservationStatus.DONE);
        // 변경된 예약을 저장합니다.
        reservationRepository.save(reservation);
    }

}

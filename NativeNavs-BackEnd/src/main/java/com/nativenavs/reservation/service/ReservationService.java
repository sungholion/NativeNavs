package com.nativenavs.reservation.service;

import com.nativenavs.reservation.dto.*;
import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.enums.ReservationStatus;
import com.nativenavs.reservation.repository.ReservationRepository;
import com.nativenavs.tour.dto.TourDTO;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import com.nativenavs.tour.service.TourService;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import com.nativenavs.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    @Autowired
    private WishlistRepository wishlistRepository;

    public ReservationEntity addReservation(ReservationRequestDTO requestDTO, int guideId) {
        UserEntity guide = userRepository.findById(guideId)
                .orElseThrow(() -> new IllegalArgumentException("Guide not found"));

        UserEntity participant = userRepository.findById(requestDTO.getParticipantId())
                .orElseThrow(() -> new IllegalArgumentException("Participant not found"));

        TourEntity tour = tourRepository.findById(requestDTO.getTourId())
                .orElseThrow(() -> new IllegalArgumentException("Tour not found"));

        ReservationEntity reservation = new ReservationEntity();
        reservation.setReservationNumber(UUID.randomUUID().toString().substring(0, 8));
        reservation.setGuide(guide);
        reservation.setTour(tour);
        reservation.setDate(requestDTO.getDate());
        reservation.setParticipant(participant);
        reservation.setStartAt(requestDTO.getStartAt());
        reservation.setMeetingAddress(requestDTO.getMeetingAddress());
        reservation.setEndAt(requestDTO.getEndAt());
        reservation.setParticipantCount(requestDTO.getParticipantCount());
        reservation.setMeetingLatitude(requestDTO.getMeetingLatitude());
        reservation.setMeetingLongitude(requestDTO.getMeetingLongitude());
        reservation.setStatus(ReservationStatus.RESERVATION);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setDescription(requestDTO.getDescription());

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

    public ReservationTourDTO getParticipantsForTour(TourEntity tour) {
        List<ReservationEntity> reservations=reservationRepository.findByTourAndStatusOrderByDateAsc(tour,ReservationStatus.RESERVATION);
        ReservationTourDTO reservationTourDTO = new ReservationTourDTO();

        reservationTourDTO.setTourDTO(TourDTO.toTourDTO(tour));
        reservationTourDTO.setBookCount(reservationRepository.countByTour(tour));
        reservationTourDTO.setWishCount(wishlistRepository.countByTourId(tour.getId()));

        List<ParticipantDTO> participants = new ArrayList<>();
        for( ReservationEntity r : reservations){
            ParticipantDTO participantDTO = new ParticipantDTO();
            participantDTO.setReservationId(r.getId());
            participantDTO.setParticipantCount(r.getParticipantCount());
            participantDTO.setReservationDate(r.getDate());
            participantDTO.setReservationNumber(r.getReservationNumber());
            participantDTO.setUserImage(r.getParticipant().getImage());
            participantDTO.setUserNickName(r.getParticipant().getNickname());
            participants.add(participantDTO);
        }
        reservationTourDTO.setReservationResponseDTOList(participants);

        return reservationTourDTO;

    }



    public void finishReservation(int reservationId) {
        // 예약을 조회합니다.
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약을 찾을 수 없습니다: " + reservationId));
        // 상태를 DONE으로 변경합니다.
        reservation.setStatus(ReservationStatus.DONE);
        reservation.setTaggingAt(LocalDateTime.now());
        // 변경된 예약을 저장합니다.
        reservationRepository.save(reservation);
    }

    public ReservationReviewDTO getReservationForReview(int reservationId){
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservationId));

        ReservationReviewDTO reservationReviewDTO = new ReservationReviewDTO();
        reservationReviewDTO.setReviewed(reservationEntity.isReviewed());
        reservationReviewDTO.setReservationId(reservationEntity.getId());
        reservationReviewDTO.setTourId(reservationEntity.getTour().getId());
        reservationReviewDTO.setDate(reservationEntity.getDate());
        reservationReviewDTO.setStatus(reservationEntity.getStatus());

        return reservationReviewDTO;
    }

}

package com.nativenavs.reservation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.tour.entity.PlanEntity;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private int tourId;
    private String tourTitle;
    private String thumbnailImage;
    private String tourLocation;
    private List<String> planImages;
    private LocalDate reservationDate;
    private LocalTime meetingStartAt;
    private LocalTime meetingEndAt;
    private float tourReviewScore;
    private UserDTO guide;

    private String reservationNumber;
    private int reservationId;

    private UserDTO participant;

    private int participantCount;
    private String meetingAddress;
    private BigDecimal meetingLatitude;
    private BigDecimal meetingLongitude;
    private String reservationDescription;

    private Integer roomId;

    public static ReservationResponseDTO toReservationDTO(ReservationEntity reservationEntity) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        TourEntity tourEntity = reservationEntity.getTour();

        dto.setTourId(tourEntity.getId());
        dto.setReservationNumber(reservationEntity.getReservationNumber());
        dto.setTourTitle(tourEntity.getTitle());
        dto.setThumbnailImage(tourEntity.getThumbnailImage());
        dto.setPlanImages(tourEntity.getPlans().stream()
                .map(PlanEntity::getImage)
                .toList());
        dto.setReservationDate(reservationEntity.getDate());
        dto.setMeetingStartAt(reservationEntity.getStartAt());
        dto.setMeetingEndAt(reservationEntity.getEndAt());
        dto.setTourReviewScore(tourEntity.getReviewAverage());

        dto.setGuide(UserDTO.toUserDTO(reservationEntity.getGuide()));
        dto.setReservationId(reservationEntity.getId());
        dto.setParticipant(UserDTO.toUserDTO(reservationEntity.getParticipant()));
        dto.setParticipantCount(reservationEntity.getParticipantCount());
        dto.setMeetingAddress(reservationEntity.getMeetingAddress());
        dto.setMeetingLatitude(reservationEntity.getMeetingLatitude());
        dto.setMeetingLongitude(reservationEntity.getMeetingLongitude());
        dto.setReservationDescription(reservationEntity.getDescription());

        dto.setRoomId(reservationEntity.getRoomId());

        return dto;
    }
}
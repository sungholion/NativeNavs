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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
    private int tourId;
    private String tourTitle;
    private String thumbnailImage; // 투어의 썸네일 이미지
    private String tourLocation; // 투어 위치
    private List<String> planImages; // 투어에 속한 각 플랜 이미지
    private LocalDate reservationDate; // 예약 날짜
    private LocalTime meetingStartAt; // 만남 시작 시간
    private LocalTime meetingEndAt; // 만남 종료 시간
    private float tourReviewScore; // 투어 상세 정보
    private UserDTO guide; // 가이드 정보

    private int reservationId; // 예약 번호

    private UserDTO participant; // 참여자 정보

    private int participantCount; // 참여 인원
    private String meetingAddress; // 만나는 장소
    private BigDecimal meetingLatitude; // 만남 장소 위도
    private BigDecimal meetingLongitude; // 만남 장소 경도
    private String reservationDescription;

    public static ReservationResponseDTO toReservationDTO(ReservationEntity reservationEntity) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        TourEntity tourEntity = reservationEntity.getTour();

        dto.setTourId(tourEntity.getId());
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
        dto.setParticipant(UserDTO.toUserDTO(reservationEntity.getParticipant())); // 참여자 정보
        dto.setParticipantCount(reservationEntity.getParticipantCount());
        dto.setMeetingAddress(reservationEntity.getMeetingAddress());
        dto.setMeetingLatitude(reservationEntity.getMeetingLatitude());
        dto.setMeetingLongitude(reservationEntity.getMeetingLongitude());
        dto.setReservationDescription(reservationEntity.getDescription());

        return dto;
    }
}
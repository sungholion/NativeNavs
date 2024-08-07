package com.nativenavs.reservation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.tour.entity.PlanEntity;
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
@JsonIgnoreProperties({"guide", "tour"})
public class ReservationResponseDTO {
    private int id; // 예약 번호
    private String thumbnailImage; // 투어의 썸네일 이미지
    private String tourTitle;
    private float tourReviewScore; // ���어 ��점
    private List<String> planImages; // 투어에 속한 각 플랜 이미지
    private LocalTime startAt; // 만남 시작 시간
    private LocalTime endAt; // 만남 종료 시간
    private LocalDate date;
    private String meetingAddress;
    private UserDTO guide; // 가이드 정보n
    private UserDTO participant; // 참여자 정보
    private int participantCount; // 참여 인원
    private BigDecimal meetingLatitude; // 만남 장소 위도
    private BigDecimal meetingLongitude; // 만남 장소 경도

    public static ReservationResponseDTO toReservationDTO(ReservationEntity reservationEntity) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(reservationEntity.getId());
        dto.setThumbnailImage(reservationEntity.getTour().getThumbnailImage());
        dto.setTourTitle(reservationEntity.getTour().getTitle());
        dto.setPlanImages(reservationEntity.getTour().getPlans().stream()
                .map(PlanEntity::getImage)
                .toList());
        dto.setTourReviewScore(reservationEntity.getTour().getReviewAverage()); // ���
        dto.setStartAt(reservationEntity.getStartAt());
        dto.setMeetingAddress(reservationEntity.getMeetingAddress());
        dto.setEndAt(reservationEntity.getEndAt());
        dto.setDate(reservationEntity.getDate());
        dto.setGuide(UserDTO.toUserDTO(reservationEntity.getGuide()));
        dto.setParticipant(UserDTO.toUserDTO(reservationEntity.getParticipant())); // 참여자 정보
        dto.setParticipantCount(reservationEntity.getParticipantCount());
        dto.setMeetingLatitude(reservationEntity.getMeetingLatitude());
        dto.setMeetingLongitude(reservationEntity.getMeetingLongitude());
        return dto;
    }
}
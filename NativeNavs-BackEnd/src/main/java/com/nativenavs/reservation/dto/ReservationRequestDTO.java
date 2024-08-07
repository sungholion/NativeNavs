package com.nativenavs.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private int tourId;
    private int participantId;
    private LocalTime startAt;
    private LocalTime endAt;
    private String meetingAddress;
    private int participantCount;
    private LocalDate date;
    private BigDecimal meetingLatitude;
    private BigDecimal meetingLongitude;
}

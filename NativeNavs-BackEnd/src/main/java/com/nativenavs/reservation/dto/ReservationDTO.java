package com.nativenavs.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private int tourId;
    private int participantId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int participantCount;
    private LocalDate date;
    private String description;
    private BigDecimal meetingLatitude;
    private BigDecimal meetingLongitude;
}

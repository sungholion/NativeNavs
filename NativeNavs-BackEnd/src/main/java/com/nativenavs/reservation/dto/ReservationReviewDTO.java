package com.nativenavs.reservation.dto;

import com.nativenavs.reservation.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationReviewDTO {
    private boolean isReviewed;
    private int reservationId;
    private int tourId;
    private LocalDate date;
    private ReservationStatus status;
}

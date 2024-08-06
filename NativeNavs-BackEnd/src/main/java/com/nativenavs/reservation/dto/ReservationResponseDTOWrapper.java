package com.nativenavs.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTOWrapper {
    private List<ReservationResponseDTO> reservationsInProgress;
    private List<ReservationResponseDTO> reservationsCompleted;
}
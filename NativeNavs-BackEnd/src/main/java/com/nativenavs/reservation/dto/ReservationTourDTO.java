package com.nativenavs.reservation.dto;

import com.nativenavs.tour.dto.GuideTourDTO;
import com.nativenavs.tour.dto.TourDTO;
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
public class ReservationTourDTO {

    private TourDTO tourDTO;
    private int bookCount;
    private int wishCount;
    private List<ReservationResponseDTO> reservationResponseDTOList;

}

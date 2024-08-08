package com.nativenavs.reservation.dto;

import com.nativenavs.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
    private String userImage;
    private String userNickName;
    private int reservationId;
    private LocalDate reservationDate;
    private String reservationNumber;
    private int participantCount;

}

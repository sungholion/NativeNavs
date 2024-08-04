package com.nativenavs.reservation.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.reservation.dto.ReservationRequestDTO;
import com.nativenavs.reservation.dto.ReservationResponseDTO;
import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.service.ReservationService;
import com.nativenavs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "reservation API", description = "reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    private UserService userService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @Operation(summary = "예약 등록 API", description = "예약을 등록할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> reservationSave(@RequestHeader("Authorization") String token,
                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                     description = ".",
                                                     required = true,
                                                     content = @Content(
                                                             mediaType = "application/json",
                                                             schema = @Schema(
                                                                     example =  "{\n" +
                                                                             "  \"tourId\": 1,\n" +
                                                                             "  \"participantId\": 10,\n" +
                                                                             "  \"startAt\": \"2024-08-10T09:00:00\",\n" +
                                                                             "  \"endAt\": \"2024-08-10T12:00:00\",\n" +
                                                                             "  \"participantCount\": 4,\n" +
                                                                             " \"date\": \"2024-08-15\",\n" +                                                                             "  \"description\": \"Extra luggage assistance needed\",\n" +
                                                                             "  \"meetingLatitude\": 37.5665,\n" +
                                                                             "  \"meetingLongitude\": 126.978\n" +
                                                                             "}"
                                                             )
                                                     )
                                             )@RequestBody ReservationRequestDTO reservationRequestDTO){
        try{
            int userId = getUserIdFromJWT(token);
            ReservationEntity reservationEntity = reservationService.addReservation(reservationRequestDTO, userId);
            return ResponseEntity.ok("예약 완료");
        } catch (Exception e) {
            e.printStackTrace();  // 실제 코드에서는 로그를 사용하세요
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약실패");
        }
    }

    @GetMapping("/{reservationId}")
    @Operation(summary = "예약 상세조회 API", description = "예약 상세정보를 조회하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "예약을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> reservationDetails(@RequestHeader("Authorization") String token,
                                                @Parameter(description = "예약 ID", required = true, example = "1") @PathVariable int reservationId){
        try{
            ReservationResponseDTO reservationResponseDTO = reservationService.getReservationDetails(reservationId);
            return ResponseEntity.ok(reservationResponseDTO);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 상세 조회 실패");
        }



    }



    //JWT에서 이메일 받아 id로 치환
    private int getUserIdFromJWT(String token){
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        return userService.changeEmailToId(email);
    }
}

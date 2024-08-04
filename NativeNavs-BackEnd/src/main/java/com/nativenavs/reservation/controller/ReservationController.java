package com.nativenavs.reservation.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.reservation.dto.ReservationRequestDTO;
import com.nativenavs.reservation.dto.ReservationResponseDTO;
import com.nativenavs.reservation.dto.ReservationResponseDTOWrapper;
import com.nativenavs.reservation.entity.ReservationEntity;
import com.nativenavs.reservation.service.ReservationService;
import com.nativenavs.tour.entity.TourEntity;
import com.nativenavs.tour.repository.TourRepository;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "reservation API", description = "reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TourRepository tourRepository;


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

    @PostMapping("/{reservationId}/cancel")
    @Operation(summary = "투어 삭제 API", description = "투어를 삭제하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "예약을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> reservationRemove(@RequestHeader("Authorization") String token,
                                               @Parameter(description = "예약 ID", required = true, example = "1") @PathVariable int reservationId) {
        try {
             int userId = getUserIdFromJWT(token);
            reservationService.removeReservation(reservationId);
            return ResponseEntity.ok("예약 취소 완료");

        } catch (Exception e) {
            // 로그 기록 (여기서는 예시로 printStackTrace 사용, 실제로는 로깅 프레임워크를 사용하는 것이 좋습니다)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 취소 실패");
        }

    }

    @GetMapping
    @Operation(summary = "Trav 예약 현황 조회 API", description = "Trav의 예약현황을 조회하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> getMyReservations(@RequestHeader("Authorization") String token) {
        try {
            int userId = getUserIdFromJWT(token);
            Optional<UserEntity> participant = userRepository.findById(userId);
            ReservationResponseDTOWrapper reservations = reservationService.getReservationsForParticipant(participant.orElse(null));
            return ResponseEntity.ok(reservations);
        }  catch (Exception e) {
            // 로그 기록 (여기서는 예시로 printStackTrace 사용, 실제로는 로깅 프레임워크를 사용하는 것이 좋습니다)
            e.printStackTrace();
            // 에러 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 에러 응답이 필요하다면 메시지를 포함할 수 있습니다.
        }
    }

    @GetMapping("/tour/{tourId}/participants")
    @Operation(summary = "투어 참여자 조회 API", description = "특정 투어에 대한 예약을 조회하고, 예약 중인 참여자 정보를 반환하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> getParticipantsForTour(
            @Parameter(description = "조회할 투어 ID", required = true, example = "1") @PathVariable int tourId, @RequestHeader("Authorization") String token) {
        try {
            int userId = getUserIdFromJWT(token); // JWT에서 가이드 ID 추출
            Optional<UserEntity> guide = userRepository.findById(userId);
            TourEntity tour = tourRepository.findById(tourId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 투어를 찾을 수 없습니다: " + tourId));
            List<ReservationResponseDTO> reservationEntities = reservationService.getParticipantsForTour(tour, guide.orElse(null));  // 가이드 정보가 null이면 null을 ����
            return ResponseEntity.ok(reservationEntities);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //JWT에서 이메일 받아 id로 치환
    private int getUserIdFromJWT(String token){
        String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        return userService.changeEmailToId(email);
    }
}

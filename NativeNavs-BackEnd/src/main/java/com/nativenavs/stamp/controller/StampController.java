package com.nativenavs.stamp.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.review.dto.TourReviewDTO;
import com.nativenavs.stamp.dto.StampDTO;
import com.nativenavs.stamp.service.StampService;
import com.nativenavs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users/stamp")
@RequiredArgsConstructor
@Tag(name = "stamp API", description = "stamp")
public class StampController {

    private final StampService stampService;
    private final UserService userService;


    @GetMapping("/{userId}")
    @Operation(summary = "스탬프 조회 API", description = "사용자의 스탬프를 조회할때 사용하는 API")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> getStampByUser(@RequestHeader("Authorization") String token,
                                                @Parameter(description = "조회 기준이 될 유저 ID", required = true, example = "1")
                                                @PathVariable("userId") int userId){

        try {
            List<StampDTO> stampList = stampService.getAllStampsByUserId(userId);
            return ResponseEntity.ok(stampList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스탬프 조회 실패");
        }

    }
    private int getUserIdFromJWT(String token){
        String jwtToken = token.replace("Bearer ", "");
        String email = JwtTokenProvider.getEmailFromToken(jwtToken);
        return userService.changeEmailToId(email);
    }

}

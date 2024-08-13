package com.nativenavs.auth.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.auth.service.AuthService;
import com.nativenavs.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
@Tag(name = "로그인 API", description = "로그인 / 로그아웃 / AccessToken 갱신")
public class AuthController {

    // DI --------------------------------------------------------------------------------------------------------------

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // API -------------------------------------------------------------------------------------------------------------

    @Operation(summary = "로그인 API", description = "email, password을 입력하여 로그인")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginByEmail(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "email, password, device (앱에서 자동 입력)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"email\": \"trav@gmail.com\", \"password\": \"ssafyd110!\", \"device\": \"ios\"}"
                            )
                    )
            )
            @RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();

        String email = credentials.get("email");
        String password = credentials.get("password");
        String device = credentials.get("device");

        try {
            UserDTO userDTO = authService.loginByEmail(email, password, device);

            if (userDTO != null) {
                String accessToken = jwtTokenProvider.generateAccessToken(email);
                String refreshToken = jwtTokenProvider.generateRefreshToken(email);
                int userId = userDTO.getId();
                Boolean userIsNav = userDTO.getIsNav();

                response.put("message", "로그인 성공");
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);
                response.put("id", userId);
                response.put("isNav", userIsNav);

                log.info("로그인 성공: email={}, device={}, id = {}, isNav = {}", email, device, userId, userIsNav);

                return ResponseEntity.ok(response);
            } else {
                log.error("로그인 실패: 잘못된 이메일 또는 비밀번호: email={}", email);
                response.put("message", "로그인 실패: 잘못된 이메일 또는 비밀번호");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            log.error("로그인 중 오류 발생 : {}", e.getMessage(), e);
            response.put("message", "로그인 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "로그아웃 API", description = "로그아웃 - accessToken을 blackList에 추가")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "액세스 토큰",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"accessToken\": \"your_access_token_here\"}"
                            )
                    )
            )
            @RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String accessToken = body.get("accessToken");

        try {
            if (jwtTokenProvider.validateToken(accessToken)) {
                // 로그아웃된 토큰을 블랙리스트에 추가
                jwtTokenProvider.invalidateToken(accessToken);
                response.put("message", "로그아웃 성공");

                log.info("로그아웃 성공: accessToken={}", accessToken);

                return ResponseEntity.ok(response);
            } else {
                response.put("message", "유효하지 않은 액세스 토큰입니다.");

                log.error("로그아웃 실패: 유효하지 않은 엑세스 토큰 - accessToken={}", accessToken);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            log.error("로그아웃 중 오류 발생 : {}", e.getMessage(), e);
            response.put("message", "로그아웃 서버 에러");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "AccessToken 갱신 API", description = "RefreshToken으로 accessToken 생성")
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshAccessToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "리프레시 토큰",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"refreshToken\": \"your_refresh_token_here\"}"
                            )
                    )
            )
            @RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String refreshToken = body.get("refreshToken");
        try {
            if (jwtTokenProvider.validateToken(refreshToken)) {
                String email = JwtTokenProvider.getEmailFromToken(refreshToken);
                String newAccessToken = jwtTokenProvider.generateAccessToken(email);
                response.put("message", "액세스 토큰 갱신 성공");
                response.put("accessToken", newAccessToken);

                log.info("액세스 토큰 갱신 성공: email={}", email);
                return ResponseEntity.ok(response);
            } else {
                log.error("액세스 토큰 갱신 실패: 유효하지 않은 리프레시 토큰 - refreshToken={}", refreshToken);
                response.put("message", "리프레시 토큰이 유효하지 않습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            log.error("AccessToken 갱신 중 오류 발생 : {}", e.getMessage(), e);
            response.put("message", "액세스 토큰 갱신 서버 에러");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
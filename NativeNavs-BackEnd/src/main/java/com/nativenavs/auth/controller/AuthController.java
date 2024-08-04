package com.nativenavs.auth.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.auth.service.AuthService;
import com.nativenavs.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
@Tag(name = "로그인/로그아웃 API", description = "로그인 / 로그아웃 / Token 갱신")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(summary = "로그인 API", description = "사용자의 이메일과 비밀번호로 로그인 합니다")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginByEmail(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "email, password, device(앱에서 자동 입력)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"email\": \"eoblue23@gmail.com\", \"password\": \"1234\", \"device\": \"ios\"}"
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
                response.put("message", "로그인 성공");
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);
                response.put("id", userDTO.getId());
                response.put("isNav", userDTO.getIsNav());
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "로그인 실패: 잘못된 이메일 또는 비밀번호");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "로그인 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "로그아웃 API", description = "사용자를 로그아웃 합니다")
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
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "유효하지 않은 액세스 토큰입니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "로그아웃 서버 에러");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "AccessToken 갱신 API", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 생성합니다")
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
                String email = jwtTokenProvider.getEmailFromToken(refreshToken);
                String newAccessToken = jwtTokenProvider.generateAccessToken(email);
                response.put("message", "액세스 토큰 갱신 성공");
                response.put("accessToken", newAccessToken);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "리프레시 토큰이 유효하지 않습니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "액세스 토큰 갱신 서버 에러");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
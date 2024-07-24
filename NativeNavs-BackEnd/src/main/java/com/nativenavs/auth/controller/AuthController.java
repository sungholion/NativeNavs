package com.nativenavs.auth.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.auth.service.AuthService;
import com.nativenavs.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Tag(name = "auth API", description = "Authentication")
    @Operation(summary = "이메일과 비밀번호로 로그인", description = "사용자의 이메일과 비밀번호로 로그인 합니다.")
    @ApiResponse(responseCode = "200", description = "로그인에 성공하였습니다.")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginSessionWithEmail(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "로그인에 필요한 email, password, device 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"email\": \"user@example.com\", \"password\": \"password123\", \"device\": \"asjdlkwej\"}"
                            )
                    )
            )
            @RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();

        String email = credentials.get("email");
        String password = credentials.get("password");
        String device = credentials.get("device");

        try {
            User user = authService.loginSessionWithEmail(email, password, device);
            if (user != null) {
                String accessToken = jwtTokenProvider.generateAccessToken(email);
                String refreshToken = jwtTokenProvider.generateRefreshToken(email);
                response.put("message", "로그인 성공");
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);
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

    @Operation(summary = "로그아웃", description = "사용자를 로그아웃 합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃에 성공하였습니다.")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        // JWT는 서버에서 세션을 관리하지 않으므로, 클라이언트 측에서 토큰을 삭제하는 것으로 로그아웃을 대체합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("message", "로그아웃 성공");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "리프레시 토큰을 통한 액세스 토큰 갱신", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 생성합니다.")
    @ApiResponse(responseCode = "200", description = "액세스 토큰 갱신 성공")
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
            response.put("message", "액세스 토큰 갱신 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
package com.nativenavs.auth.controller;

import com.nativenavs.auth.service.AuthService;
import com.nativenavs.user.model.User;
import com.nativenavs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*") // 아직 고민..
public class AuthController {

    @Autowired
    private AuthService authService;

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
            @RequestBody Map<String, String> credentials, HttpSession session){

        Map<String, Object> response = new HashMap<>();

        if(session.getAttribute("user") != null){
            response.put("message", "이미 로그인된 유저입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        String email = credentials.get("email");
        String password = credentials.get("password");
        String device = credentials.get("device");

        try {
            User user = authService.loginSessionWithEmail(email, password, device);
            if (user != null) {
                session.setAttribute("user", user);
                response.put("message", "로그인 성공");
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
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        Map<String, Object> response = authService.logout(session);

        if ("로그아웃 성공".equals(response.get("message"))) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}

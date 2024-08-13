package com.nativenavs.user.controller;

import com.nativenavs.auth.jwt.JwtTokenProvider;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.dto.UserSearchDTO;
import com.nativenavs.user.service.EmailService;
import com.nativenavs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원 API", description = "중복 체크 / 이메일 인증 / 회원 가입 CRUD / FCM Token")
public class UserController {

    // DI --------------------------------------------------------------------------------------------------------------

    private final UserService userService;
    private final EmailService emailService;

    // API -------------------------------------------------------------------------------------------------------------

    @Operation(summary = "email 중복 체크 API", description = "회원 가입 시 email이 중복인지 확인")
    @GetMapping("/checkDuplicated/email/{email}")
    public ResponseEntity<String> checkDuplicatedEmail(
            @Parameter(description = "Email", required = true, example = "trav@gmail.com")
            @PathVariable("email") String email) {
        log.info("이메일 중복 체크 요청: {}", email);
        try {
            boolean isDuplicated = userService.checkDuplicatedEmail(email);

            if (!isDuplicated) {
                log.info("사용 가능한 이메일입니다: {}", email);
                return ResponseEntity.ok("사용 가능한 email");
            } else {
                log.warn("중복된 이메일입니다: {}", email);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 email");
            }
        } catch (Exception e) {
            log.error("이메일 중복 체크 중 오류 발생: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Operation(summary = "nickname 중복 체크 API", description = "회원 가입 시 nickname이 중복인지 확인")
    @GetMapping("/checkDuplicated/nickname/{nickname}")
    public ResponseEntity<String> checkDuplicatedNickname(
            @Parameter(description = "Nickname", required = true, example = "bts")
            @PathVariable("nickname") String nickname) {
        log.info("닉네임 중복 체크 요청: {}", nickname);
        try {
            boolean isDuplicated = userService.checkDuplicatedNickname(nickname);

            if (!isDuplicated) {
                log.info("사용 가능한 닉네임입니다: {}", nickname);
                return ResponseEntity.ok("사용 가능한 nickname 입니다");
            } else {
                log.warn("중복된 닉네임입니다: {}", nickname);
                return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 nickname 입니다");
            }
        } catch (Exception e) {
            log.error("닉네임 중복 체크 중 오류 발생: {}", nickname, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Operation(summary = "이메일 발송 API", description = "email을 입력하여 인증코드를 발송")
    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(
            @Parameter(description = "이메일 주소", required = true, example = "eoblue23@gmail.com")
            @RequestParam("email") String email) {
        log.info("이메일 발송 요청: {}", email);
        try {
            if (userService.checkDuplicatedEmail(email)) {
                log.warn("이미 존재하는 이메일입니다: {}", email);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 email 입니다");
            } else {
                emailService.sendAuthenticationCodeEmail(email);
                log.info("이메일 발송 성공: {}", email);
                return ResponseEntity.accepted().body("이메일 발송에 성공했습니다");
            }
        } catch (Exception e) {
            log.error("이메일 발송 중 오류 발생: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Operation(summary = "이메일 인증 API", description = "email, authenticationCode를 입력하여 이메일 인증")
    @GetMapping("/authenticateEmail")
    public ResponseEntity<?> authenticateEmail(
            @Parameter(description = "이메일 주소", required = true, example = "eoblue23@gmail.com")
            @RequestParam("email") String email,
            @Parameter(description = "인증 코드", required = true, example = "")
            @RequestParam("authenticationCode") String authenticationCode) {
        log.info("이메일 인증 요청: {} / 인증 코드: {}", email, authenticationCode);
        try {
            if(emailService.authenticateEmail(email, authenticationCode)) {
                log.info("이메일 인증 성공: {}", email);
                return ResponseEntity.ok("이메일 인증에 성공했습니다");
            } else {
                log.warn("이메일 인증 실패: {} / 인증 코드 불일치", email);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다");
            }
        } catch (Exception e) {
            log.error("이메일 인증 중 오류 발생: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Operation(summary = "회원가입 API", description = "회원가입 페이지를 통해 입력한 정보로 회원가입 (이메일 인증 필수)")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> signUp (
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "이메일, 패스워드, 이미지, 이름, 생년월일(Date), 전화번호('-' 없이 String), 국가, " +
                            "닉네임, 구사 가능 언어(최대 3개, String으로 이루어진 List, 추후로 미룸.), " +
                            "가이드인지 여부(T/F), 기기(자동으로 입력)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"email\": \"eoblue23@gmail.com\", \"password\": \"1234\", " +
                                            "\"isNav\": false," +
                                            "\"nickname\": \"bts\", \"nation\": \"USA\"," +
                                            "\"userLanguage\": \"english, japanese\"," +
                                            "\"name\": \"kevin\", \"birth\": \"1998-08-23\"," +
                                            "\"phone\": \"01012345678\", \"device\": \"ios\"}"
                            )
                    )
            )
            @RequestPart("user") UserDTO user,
            @RequestPart("profileImage") MultipartFile profileImage) {
        log.info("회원가입 요청: {}", user.getEmail());
        try {
            if (userService.checkDuplicatedEmail(user.getEmail())) {
                log.warn("이미 존재하는 이메일로 회원가입 시도: {}", user.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 email 입니다");
            }

            userService.signUp(user, profileImage);
            log.info("회원가입 성공: {}", user.getEmail());
            return ResponseEntity.accepted().body("회원 가입에 성공했습니다");
        } catch (Exception e) {
            log.error("회원가입 중 오류 발생: {}", user.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(summary = "전체 회원 검색 API", description = "가입된 전체 회원을 검색")
    @GetMapping("/search/all")
    public ResponseEntity<?> searchAllUser() {
        log.info("전체 회원 검색 요청");
        try {
            return new ResponseEntity<>(userService.searchAllUser(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("전체 회원 검색 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Operation(summary = "특정 회원 검색 API", description = "Id를 입력하여 특정 회원 1명 조회")
    @GetMapping("/search/id/{id}")
    public ResponseEntity<?> searchById(
            @Parameter(description = "Id", required = true, example = "5")
            @PathVariable("id") int id) {
        log.info("특정 회원 검색 요청: id={}", id);
        try {
            UserSearchDTO user = userService.searchById(id);

            if(user != null) {
                log.info("회원 검색 성공: id={}", id);
                return ResponseEntity.accepted().body(user);
            } else {
                log.warn("회원 검색 실패: 없는 id={}", id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            log.error("회원 검색 중 오류 발생: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Operation(summary = "Email 회원 검색 API", description = "email을 입력하여 특정 회원 1명을 조회")
    @GetMapping("/search/email/{email}")
    public ResponseEntity<?> searchByEmailForClient(
            @Parameter(description = "이메일 주소", required = true, example = "eoblue23@gmail.com")
            @PathVariable("email") String email) {
        log.info("이메일로 회원 검색 요청: {}", email);
        try {
            UserSearchDTO user = userService.searchByEmailForClient(email);

            if(user != null) {
                log.info("이메일로 회원 검색 성공: {}", email);
                return ResponseEntity.accepted().body(user);
            } else {
                log.warn("회원 검색 실패: 없는 이메일={}", email);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            log.error("이메일로 회원 검색 중 오류 발생: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Operation(summary = "name 회원 검색 API", description = "name을 입력하여 특정 회원 1명 조회")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/search/name/{name}")
    public ResponseEntity<?> searchByName(
            @Parameter(description = "Name", required = true, example = "kevin")
            @PathVariable("name") String name) {
        log.info("이름으로 회원 검색 요청: {}", name);
        try {
            UserSearchDTO user = userService.searchByName(name);

            if(user != null) {
                log.info("이름으로 회원 검색 성공: {}", name);
                return ResponseEntity.accepted().body(user);
            } else {
                log.warn("회원 검색 실패: 없는 이름={}", name);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            log.error("이름으로 회원 검색 중 오류 발생: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Operation(summary = "nickname 회원 검색 API", description = "nickname을 입력하여 특정 회원 1명을 조회")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/search/nickname/{nickname}")
    public ResponseEntity<?> searchByNickname(
            @Parameter(description = "Nickname", required = true, example = "bluebird")
            @PathVariable("nickname") String nickname) {
        log.info("닉네임으로 회원 검색 요청: {}", nickname);
        try {
            UserSearchDTO user = userService.searchByNickname(nickname);

            if(user != null) {
                log.info("닉네임으로 회원 검색 성공: {}", nickname);
                return ResponseEntity.accepted().body(user);
            } else {
                log.warn("회원 검색 실패: 없는 닉네임={}", nickname);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            log.error("닉네임으로 회원 검색 중 오류 발생: {}", nickname, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(summary = "회원 정보 수정 API", description = "회원 정보 수정")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestPart("user") UserDTO updateUserDTO,
            @RequestPart("profileImage") MultipartFile profileImage) {
        log.info("회원 정보 수정 요청: email={}", updateUserDTO.getEmail());
        try {
            String jwtToken = token.replace("Bearer ", "");
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);

            UserDTO existingUserDTO = userService.searchByEmail(email);
            if (existingUserDTO == null) {
                log.warn("회원 정보 수정 실패: 인증되지 않은 사용자");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
            }

            userService.updateUser(existingUserDTO.getId(), updateUserDTO, profileImage);
            log.info("회원 정보 수정 성공: email={}", email);
            return ResponseEntity.ok("회원 정보 수정에 성공했습니다");

        } catch (Exception e) {
            log.error("회원 정보 수정 중 오류 발생: email={}", updateUserDTO.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 입니다");
        }
    }

    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(
            @RequestHeader("Authorization") String token) {
        log.info("회원 탈퇴 요청");
        try {
            String jwtToken = token.replace("Bearer ", "");
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);

            UserDTO existingUser = userService.searchByEmail(email);
            if (existingUser == null) {
                log.warn("회원 탈퇴 실패: 인증되지 않은 사용자");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
            }

            userService.deleteUser(existingUser.getId());
            log.info("회원 탈퇴 성공: email={}", email);
            return ResponseEntity.ok("회원 탈퇴에 성공했습니다");

        } catch (Exception e) {
            log.error("회원 탈퇴 중 오류 발생: email={}", JwtTokenProvider.getEmailFromToken(token.replace("Bearer ", "")), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 입니다");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Operation(summary = "Email을 ID로 전환하는 API", description = "email을 입력하여 해당 id를 조회")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/changeEmailToId/{email}")
    public ResponseEntity<?> changeEmailToId(
            @Parameter(description = "Email", required = true, example = "eoblue23@gmail.com")
            @PathVariable("email") String email) {
        log.info("이메일을 ID로 전환 요청: {}", email);
        try {
            int id = userService.changeEmailToId(email);

            if(id != 0) {
                log.info("이메일을 ID로 전환 성공: email={} / id={}", email, id);
                return ResponseEntity.accepted().body(id);
            } else {
                log.warn("이메일을 ID로 전환 실패: 없는 이메일={}", email);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원");
            }
        } catch (Exception e) {
            log.error("이메일을 ID로 전환 중 오류 발생: {}", email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 검색 실패");
        }
    }

    @Operation(summary = "FCM 토큰 업데이트 API", description = "사용자의 FCM 토큰을 업데이트")
    @PutMapping("/fcmToken")
    public ResponseEntity<?> updateFcmToken(
            @Parameter(description = "User ID", required = true, example = "5")
            @RequestParam("userId") int userId,
            @Parameter(description = "FCM Token", required = true, example = "some-fcm-token")
            @RequestParam("fcmToken") String fcmToken) {
        log.info("FCM 토큰 업데이트 요청: userId={}", userId);
        try {
            userService.updateFcmToken(userId, fcmToken);
            log.info("FCM 토큰 업데이트 성공: userId={}", userId);
            return ResponseEntity.ok("FCM 토큰이 업데이트 되었습니다");
        } catch (Exception e) {
            log.error("FCM 토큰 업데이트 중 오류 발생: userId={}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 입니다");
        }
    }

    @Operation(summary = "FcmToken 조회 API", description = "FcmToken을 조회")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/search/fcmToken/{userId}")
    public ResponseEntity<?> getFcmTokenById(
            @Parameter(description = "userId", required = true, example = "4")
            @PathVariable("userId") int userId) {
        log.info("FCM 토큰 조회 요청: userId={}", userId);
        try {
            UserSearchDTO user = userService.searchById(userId);

            if(user != null) {
                String token = user.getFcmToken();
                log.info("FCM 토큰 조회 성공: userId={}, token={}", userId, token);
                return ResponseEntity.accepted().body(token);
            } else {
                log.warn("FCM 토큰 조회 실패: 없는 userId={}", userId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            log.error("FCM 토큰 조회 중 오류 발생: userId={}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }
}

/*
    리팩토링



 */

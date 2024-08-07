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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*") // 아직 고민..
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    // -----------------------------------------------------------------------------------------------------------------

    @Tag(name = "회원 중복 체크 API", description = "중복 체크 - email / nickname")
    @Operation(summary = "email 중복 체크 API", description = "email 중복 체크를 한다")
    @GetMapping("/checkDuplicated/email/{email}")
    public ResponseEntity<String> checkDuplicatedEmail(
            @Parameter(
                    description = "Email",
                    required = true,
                    example = "eoblue23@gmail.com"
            )
            @PathVariable("email") String email) {
        try {
            boolean isDuplicated = userService.checkDuplicatedEmail(email);

            if (!isDuplicated) {
                return ResponseEntity.ok("사용 가능한 email 입니다");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 email 입니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    @Tag(name = "회원 중복 체크 API", description = "중복 체크 - email / nickname")
    @Operation(summary = "nickname 중복 체크 API", description = "nickname 중복 체크를 한다")
    @GetMapping("/checkDuplicated/nickname/{nickname}")
    public ResponseEntity<String> checkDuplicatedNickname(
            @Parameter(
                    description = "Nickname",
                    required = true,
                    example = "bts"
            )
            @PathVariable("nickname") String nickname) {
        try {
            boolean isDuplicated = userService.checkDuplicatedNickname(nickname);

            if (!isDuplicated) {
                return ResponseEntity.ok("사용 가능한 nickname 입니다");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 nickname 입니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 입니다");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Tag(name = "이메일 발송/인증, 회원가입 API", description = "회원가입 관련 - 이메일 발송 / 인증 / 회원가입")
    @Operation(summary = "이메일 발송 API", description = "email을 입력하여 인증코드를 발송합니다")
    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(
            @Parameter(
                    description = "이메일 주소",
                    required = true,
                    example = "eoblue23@gmail.com"
            )
            @RequestParam("email") String email) {
        try {
            if (userService.checkDuplicatedEmail(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 email 입니다");
            } else {
                emailService.sendAuthenticationCodeEmail(email);
                return ResponseEntity.accepted().body("이메일 발송에 성공했습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버 에러 입니다");
        }
    }

    @Tag(name = "이메일 발송/인증, 회원가입 API", description = "회원가입 관련 - 이메일 발송 / 인증 / 회원가입")
    @Operation(summary = "이메일 인증 API", description = "이메일과 인증 코드를 입력하여 이메일 인증을 합니다.")
    @GetMapping("/authenticateEmail")
    public ResponseEntity<?> authenticateEmail(
            @Parameter(
                    description = "이메일 주소",
                    required = true,
                    example = "eoblue23@gmail.com"
            ) @RequestParam("email") String email,
            @Parameter(
                    description = "인증 코드",
                    required = true,
                    example = ""
            ) @RequestParam("authenticationCode") String authenticationCode) {
        try {
            if(emailService.authenticateEmail(email, authenticationCode)) {
                return ResponseEntity.ok("이메일 인증에 성공했습니다");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버 에러 입니다");
        }
    }

    @Tag(name = "이메일 발송/인증, 회원가입 API", description = "회원가입 관련 - 이메일 발송 / 인증 / 회원가입")
    @Operation(summary = "회원가입 API", description = "회원가입을 합니다. (이메일 발송/인증 필수)")
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
            @RequestPart("profileImage") MultipartFile profileImage)           //@RequestBody UserDTO user
             {
        try {
            if (userService.checkDuplicatedEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 email 입니다");
            }

            userService.signUp(user,profileImage);
            return ResponseEntity.accepted().body("회원 가입에 성공했습니다");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버 에러 입니다");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Tag(name = "회원 검색 API", description = "조회 - 전체 / id / email / nickname / name")
    @Operation(summary = "전체 회원 검색 API", description = "가입된 전체 회원 목록을 검색합니다")
    @GetMapping("/search/all")
    public ResponseEntity<?> searchAllUser() {
        try{
            return new ResponseEntity<>(userService.searchAllUser(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버 에러 입니다");
        }
    }

    @Tag(name = "회원 검색 API", description = "조회 - 전체 / id / email / nickname / name")
    @Operation(summary = "id로 회원 검색 API", description = "Id를 입력하여 특정 회원 1명을 조회합니다")
    @GetMapping("/search/id/{id}")
    public ResponseEntity<?> searchById(
            @Parameter(
                    description = "Id",
                    required = true,
                    example = "5"
            )
            @PathVariable("id") int id) {
        try {
            UserSearchDTO user = userService.searchById(id);

            if(user != null) {
                return ResponseEntity.accepted().body(user);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버 에러 입니다");
        }
    }

    @Tag(name = "회원 검색 API", description = "조회 - 전체 / id / email / nickname / name")
    @Operation(summary = "Email로 회원 검색 API", description = "email을 입력하여 특정 회원 1명을 조회합니다")
    @GetMapping("/search/email/{email}")
    public ResponseEntity<?> searchByEmailForClient(
            @Parameter(
                    description = "이메일 주소",
                    required = true,
                    example = "eoblue23@gmail.com"
            )
            @PathVariable("email") String email) {
        try {
            UserSearchDTO user = userService.searchByEmailForClient(email);

            if(user != null) {
                return ResponseEntity.accepted().body(user);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버 에러 입니다");
        }
    }

    @Tag(name = "회원 검색 API", description = "조회 - 전체 / id / email / nickname / name")
    @Operation(summary = "name으로 회원 검색 API", description = "name을 입력하여 특정 회원 1명을 조회합니다")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/search/name/{name}")
    public ResponseEntity<?> searchByName(
            @Parameter(
                    description = "Name",
                    required = true,
                    example = "kevin"
            )
            @PathVariable("name") String name) {
        try {
            UserSearchDTO user = userService.searchByName(name);

            if(user != null) {
                return ResponseEntity.accepted().body(user);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버 에러 입니다");
        }
    }

    @Tag(name = "회원 검색 API", description = "조회 - 전체 / id / email / nickname / name")
    @Operation(summary = "nickname으로 회원 검색 API", description = "nickname을 입력하여 특정 회원 1명을 조회합니다")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/search/nickname/{nickname}")
    public ResponseEntity<?> searchByNickname(
            @Parameter(
                    description = "Nickname",
                    required = true,
                    example = "bluebird"
            )
            @PathVariable("nickname") String nickname) {
        try {
            UserSearchDTO user = userService.searchByNickname(nickname);

            if(user != null) {
                return ResponseEntity.accepted().body(user);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원입니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("서버 에러 입니다");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Tag(name = "회원 정보 수정 / 탈퇴 API", description = "정보 수정, 탈퇴")
    @Operation(summary = "회원 정보 수정 API", description = "회원 정보를 수정합니다")
    @PutMapping
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = ".",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    example = "{\"image\": \"profile.png\", \"nickname\": \"supernova\", " +
                                            "\"userLanguage\": \"russian, spanish\",\"phone\": \"01045678989\"," +
                                            "\"password\": \"4567\"}"
                            )
                    )
            )
            @RequestPart("user") UserDTO updateUserDTO,
            @RequestPart("profileImage") MultipartFile profileImage)
//            @RequestBody UserDTO updateUserDTO
    {
        try {
            String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);

            UserDTO existingUserDTO = userService.searchByEmail(email);
            if (existingUserDTO == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
            }

            userService.updateUser(existingUserDTO.getId(), updateUserDTO, profileImage);
            return ResponseEntity.ok("회원 정보 수정에 성공했습니다");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 입니다");
        }
    }

    @Tag(name = "회원 정보 수정 / 탈퇴 API", description = "정보 수정, 탈퇴")
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴를 합니다")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(
            @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.replace("Bearer ", ""); // "Bearer " 부분 제거
            String email = JwtTokenProvider.getEmailFromToken(jwtToken);

            UserDTO existingUser = userService.searchByEmail(email);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다");
            }

            userService.deleteUser(existingUser.getId());
            return ResponseEntity.ok("회원 탈퇴에 성공했습니다");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 입니다");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Tag(name = "Email <-> Id 변환ㅑㅅ API", description = "user Email을 id로 전환")
    @Operation(summary = "Email을 ID로 전환하는 API", description = "이메일을 입력하여 해당 id를 얻습니다")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/changeEmailToId/{email}")
    public ResponseEntity<?> changeEmailToId(
            @Parameter(
                    description = "Email",
                    required = true,
                    example = "eoblue23@gmail.com"
            )
            @PathVariable("email") String email) {
        try {
            int id = userService.changeEmailToId(email);

            if(id != 0) {
                return ResponseEntity.accepted().body(id);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 검색 실패");
        }
    }

}






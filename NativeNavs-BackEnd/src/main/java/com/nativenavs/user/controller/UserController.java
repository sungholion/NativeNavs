package com.nativenavs.user.controller;

import com.nativenavs.user.model.User;
import com.nativenavs.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*") // 아직 고민..
public class UserController {

    @Autowired
    private UserService userService;

    @Tag(name = "user API", description = "user")
    @Operation(summary = "회원가입 API", description = "유저가 회원가입할 때 사용하는 API")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            if (userService.checkDuplicatedEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일");
            }
            userService.signUp(user);
            return ResponseEntity.accepted().body("회원 가입 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 실패");
        }
    }



    @Tag(name = "user API", description = "user")
    @Operation(summary = "특정 회원 검색 API", description = "email을 입력하여 특정 회원 1명을 조회할 때 사용하는 API")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping("/{email}")
    public ResponseEntity<?> searchOneUser(@PathVariable("email") String email) {
        try {
            User user = userService.searchOneUser(email);

            if(user != null) {
                return ResponseEntity.accepted().body(user);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("없는 회원");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 검색 실패");
        }
    }

    @Tag(name = "user API", description = "user")
    @Operation(summary = "전체 회원 조회 API", description = "전체 회원을 조회할 때 사용하는 API")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<?> searchAllUser() {
        return new ResponseEntity<List<User>>(userService.searchAllUser(), HttpStatus.OK);
    }

    @Tag(name = "user API", description = "user")
    @Operation(summary = "회원 정보 수정 API", description = "회원 정보를 수정할 때 사용하는 API")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @PutMapping
    public ResponseEntity<?> updateUser(HttpSession session, @RequestBody User updateUser) {
        try{
            User existingUser= (User) session.getAttribute("user");

            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }

            userService.updateUser(existingUser.getId(), updateUser);
            return ResponseEntity.ok("회원 정보 수정 성공");

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 업데이트 중 서버 오류 발생");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(HttpSession session) {
        try {
            User existingUser= (User) session.getAttribute("user");
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }

            userService.deleteUser(existingUser.getId());
            return ResponseEntity.ok("회원 탈퇴 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 삭제 서버 오류");
        }
//            if(result != 0) {
//                session.removeAttribute("member");
//                return ResponseEntity.accepted().body("회원 삭제 성공");
//            }


    }




}

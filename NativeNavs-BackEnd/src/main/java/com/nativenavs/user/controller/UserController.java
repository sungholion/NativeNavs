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
@RequestMapping("/users")
@CrossOrigin("*") // 아직 고민..
public class UserController {

    @Autowired
    private UserService userService;

    @Tag(name = "user 관련 API", description = "user")
    @Operation(summary = "회원가입 API", description = "회원가입 할 때 사용하는 API")
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

    @Tag(name = "user 관련 API", description = "user")
    @Operation(summary = "email로 회원 검색 API", description = "특정 email을 가진 회원을 조회할 때 사용하는 API")
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

    @Tag(name = "user 관련 API", description = "user")
    @Operation(summary = "전체 회원 조회 API", description = "전체 회원을 조회할 때 사용하는 API")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @GetMapping
    public ResponseEntity<?> searchAllUser() {
        return new ResponseEntity<List<User>>(userService.searchAllUser(), HttpStatus.OK);
    }

//    @Tag(name = "user 관련 API", description = "user")
//    @Operation(summary = "회원 수정 API", description = "회원을 수정할 때 사용하는 API")
//    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
//    @PutMapping
//    public ResponseEntity<?> updateUser() {
//
//    }






}

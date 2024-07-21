package com.nativenavs.user.controller;

import com.nativenavs.user.model.User;
import com.nativenavs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("*") // 아직 고민..
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody User user) {
//        try {
//            if (userService.checkDuplicatedEmail(user.getEmail())) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일");
//            }
            userService.signUp(user);
            return ResponseEntity.accepted().body("회원 가입 성공");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 가입 실패");
//        }
    }


}

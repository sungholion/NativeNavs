package com.nativenavs.auth.service;

import com.nativenavs.user.mapper.UserMapper;
import com.nativenavs.user.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserMapper userMapper;

    // 이메일 로그인 로직
    public User loginSessionWithEmail(String email, String password, String device) {
        User user = userMapper.loginSessionWithEmail(email, password);
        user.setDevice(device);
        return user;
    }

    public Map<String, Object> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        if (session.getAttribute("user") != null) {
            session.invalidate();
            response.put("message", "로그아웃 성공");
        } else {
            response.put("message", "이미 로그아웃된 상태입니다.");
        }

        return response;
    }

}

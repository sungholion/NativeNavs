package com.nativenavs.auth.service;

import com.nativenavs.auth.mapper.AuthMapper;
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
    private AuthMapper authMapper;

    // 이메일 로그인 로직
    public User loginByEmail(String email, String password, String device) {
        User user = authMapper.loginByEmail(email, password);
        user.setDevice(device);
        return user;
    }


}

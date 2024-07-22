package com.nativenavs.auth.service;

import com.nativenavs.user.mapper.UserMapper;
import com.nativenavs.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserMapper userMapper;

    // 이메일 로그인 로직
    public User loginSessionWithEmail(String email, String password) {
        return userMapper.findByEmailAndPassword(email, password);
    }
}

package com.nativenavs.auth.service;

import com.nativenavs.auth.mapper.AuthMapper;
import com.nativenavs.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AuthMapper authMapper;

    // 이메일 로그인 로직
    public UserDTO loginByEmail(String email, String password, String device) {
        UserDTO userDTO = authMapper.loginByEmail(email, password);
        userDTO.setDevice(device);
        return userDTO;
    }

}

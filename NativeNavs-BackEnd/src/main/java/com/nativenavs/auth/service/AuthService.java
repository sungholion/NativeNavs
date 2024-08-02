package com.nativenavs.auth.service;

import com.nativenavs.user.dto.UserDTO;

public interface AuthService {
    public UserDTO loginByEmail(String email, String password, String device);

}

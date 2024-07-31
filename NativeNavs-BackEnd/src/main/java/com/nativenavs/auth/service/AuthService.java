package com.nativenavs.auth.service;

import com.nativenavs.user.model.User;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface AuthService {
    public User loginByEmail(String email, String password, String device);

}

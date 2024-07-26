package com.nativenavs.auth.service;

import com.nativenavs.user.model.User;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface AuthService {
    public User loginSessionWithEmail(String email, String password, String device);
    public Map<String, Object> logout(HttpSession session);



}

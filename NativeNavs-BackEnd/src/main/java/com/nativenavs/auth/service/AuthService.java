package com.nativenavs.auth.service;

import com.nativenavs.user.model.User;

public interface AuthService {
    public User loginSessionWithEmail(String email, String password, String device);
}

package com.nativenavs.user.service;

import java.util.Map;

public interface EmailService {
    public void sendAuthenticationCodeEmail(String to);
    public boolean authenticateEmail(String email, String authenticationCode);
}


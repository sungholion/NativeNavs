package com.nativenavs.user.service;

public interface EmailService {
    public void sendAuthenticationCodeEmail(String to, String subject, String text);
}

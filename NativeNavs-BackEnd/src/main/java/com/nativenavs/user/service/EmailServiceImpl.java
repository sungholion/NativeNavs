package com.nativenavs.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    private final Map<String, String> authenticationStore = new ConcurrentHashMap<>();

    public void sendAuthenticationCodeEmail(String email) {
        String authenticationCode = generateAuthenticationCode();
        String subject = "인증 코드를 확인해주세요";
        String text = "이메일 인증을 완료하기 위해, 다음 코드를 입력해주세요: " + authenticationCode;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        authenticationStore.put(email, authenticationCode);

    }

    @Override
    public boolean authenticateEmail(String email,String authenticationCode) {
        String storeCode = authenticationStore.get(email);

        if(storeCode != null && storeCode.equals(authenticationCode)) {
            authenticationStore.remove(email);
            userService.addAuthenticatedUser(email);
            return true;
        }

        return false;
    }

    private String generateAuthenticationCode() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        return String.format("%06d", num);
    }



}

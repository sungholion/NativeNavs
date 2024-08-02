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

    private final Map<String, String> authenticationStore = new ConcurrentHashMap<>();  // 이메일과 인증 코드를 임시 저장

    public void sendAuthenticationCodeEmail(String email) {
        String authenticationCode = generateAuthenticationCode();
        String subject = "인증 코드를 확인해주세요";
        String text = "이메일 인증을 완료하기 위해, 다음 코드를 입력해주세요: " + authenticationCode;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        authenticationStore.put(email, authenticationCode); // 메모리 저장소에 회원 이메일과 매치되는 인증코드 저장
    }

    private String generateAuthenticationCode() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000000);
        return String.format("%06d", num);  // 랜덤 숫자 6자리
    }

    @Override
    public boolean authenticateEmail(String email,String authenticationCode) {
        String storeCode = authenticationStore.get(email);  // 메모리 저장소에 있는 인증코드와, 입력 받은 이메일과 매칭

        if(storeCode != null && storeCode.equals(authenticationCode)) {
            authenticationStore.remove(email);  // 저장소에 있던 인증 코드 삭제
            userService.addAuthenticatedUser(email);    // userService에 인증된 회원임을 추가함
            return true;
        }

        return false;
    }
}

package com.nativenavs.user.service;

import com.nativenavs.user.mapper.UserMapper;
import com.nativenavs.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public void signUp(User user) {
        String authenticationCode = UUID.randomUUID().toString();
        user.setAuthentificationCode(authenticationCode);
        userMapper.signUp(user);

        String subject = "Please verify your email address";
        String text = "To verify your email address, please click the link below:\n" +
                "http://localhost:8080/api/users/verify?token=" + authenticationCode;
        emailService.sendAuthenticationCodeEmail(user.getEmail(), subject, text);
    }

    @Override
    public boolean checkDuplicatedEmail(String email) {
        User user = userMapper.searchOneUser(email);
        return user != null;
    }

    @Override
    public User searchOneUser(String email) {
        return userMapper.searchOneUser(email);
    }

    @Override
    public List<User> searchAllUser() {
        return userMapper.searchAllUser();
    }

    @Override
    public void updateUser(int existingId, User updateUser){
        userMapper.updateUser(existingId, updateUser);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }

    @Override
    public boolean authenticateEmail(String authenticationCode) {
        User user = userMapper.findByAuthenticationCode(authenticationCode);
        if (user != null) {
            user.setAuthenticated(true);
            user.setAuthenticationCode(null);
            userMapper.updateUser(user.getId(), user);
            return true;
        }

        return false;
    }
}

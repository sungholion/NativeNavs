package com.nativenavs.user.service;

import com.nativenavs.user.mapper.UserMapper;
import com.nativenavs.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void signUp(User user) {
        userMapper.signUp(user);
    }

    @Override
    public boolean checkDuplicatedEmail(String email) {
        return userMapper.checkDuplicatedEmail(email);
    }

    @Override
    public User searchUser(String email) {
        return userMapper.searchUser(email);
    }
}

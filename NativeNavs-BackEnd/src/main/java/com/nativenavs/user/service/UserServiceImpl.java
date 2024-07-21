package com.nativenavs.user.service;

import com.nativenavs.user.mapper.UserMapper;
import com.nativenavs.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User user = userMapper.search(email);
        return user != null;
    }

    @Override
    public User search(String email) {
        return userMapper.search(email);
    }

    @Override
    public List<User> searchAll() {
        return userMapper.searchAll();
    }
}

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
    //
    //
}

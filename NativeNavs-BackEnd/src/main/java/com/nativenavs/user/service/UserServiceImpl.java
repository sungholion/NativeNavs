package com.nativenavs.user.service;

import com.nativenavs.user.mapper.UserMapper;
import com.nativenavs.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    private final Set<String> authenticatedUsers = ConcurrentHashMap.newKeySet();

    @Override
    public void signUp(User user) {
        if(!authenticatedUsers.contains(user.getEmail())){
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }
        userMapper.signUp(user);
        authenticatedUsers.remove(user.getEmail());
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
    public void addAuthenticatedUser(String email){
        authenticatedUsers.add(email);
    }

}

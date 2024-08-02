package com.nativenavs.user.service;

import com.nativenavs.user.mapper.UserMapper;
import com.nativenavs.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private final Set<String> authenticatedUsers = ConcurrentHashMap.newKeySet();   // 인증 회원을 임시 저장

    @Override
    public boolean checkDuplicatedEmail(String email) {
        return userMapper.checkDuplicatedEmail(email);
    }
    public boolean checkDuplicatedNickname(String nickname) {
        return userMapper.checkDuplicatedNickname(nickname);
    }

    @Override
    public void signUp(User user) {
        if(!authenticatedUsers.contains(user.getEmail())){  // 이메일 미인증시
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }

        userMapper.signUp(user);
        authenticatedUsers.remove(user.getEmail()); // 메모리 저장소에서 인증 회원 제거 (가입 완료했으니)
    }

    @Override
    public List<User> searchAllUser() {
        return userMapper.searchAllUser();
    }

    @Override
    public User searchByEmail(String email) {
        return userMapper.searchByEmail(email);
    }

    @Override
    public User searchById(int id) {
        return userMapper.searchById(id);
    }

    @Override
    public User searchByNickname(String nickname) {
        return userMapper.searchByNickname(nickname);
    }

    @Override
    public User searchByName(String name) {
        return userMapper.searchByName(name);
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

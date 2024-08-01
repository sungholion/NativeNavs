package com.nativenavs.user.service;

import com.nativenavs.user.model.User;

import java.util.List;

public interface UserService {
    public boolean checkDuplicatedEmail(String email);
    public boolean checkDuplicatedNickname(String nickname);

    public void signUp(User user);
    public void updateUser(int existingId, User updateUser);
    public void deleteUser(int id);

    public List<User> searchAllUser();
    public User searchByEmail(String email);
    public User searchById(int id);
    public User searchByNickname(String nickname);
    public User searchByName(String name);

    public void addAuthenticatedUser(String email);
}

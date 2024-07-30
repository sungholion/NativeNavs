package com.nativenavs.user.service;

import com.nativenavs.user.model.User;

import java.util.List;

public interface UserService {
    public void signUp(User user);

    public boolean checkDuplicatedEmail(String email);

    public User searchOneUser(String email);
    public List<User> searchAllUser();
    public void updateUser(int existingId, User updateUser);
    public void deleteUser(int id);
    public boolean authenticateEmail(String authenticationCode);
}

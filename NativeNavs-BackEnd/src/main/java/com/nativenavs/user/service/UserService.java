package com.nativenavs.user.service;

import com.nativenavs.user.model.User;

import java.util.List;

public interface UserService {
    public boolean checkDuplicatedEmail(String email);
    public void signUp(User user);

    public List<User> searchAllUser();
    public User searchOneUser(String email);
    public void updateUser(int existingId, User updateUser);
    public void deleteUser(int id);

    public void addAuthenticatedUser(String email);
}

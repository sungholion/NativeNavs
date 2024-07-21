package com.nativenavs.user.service;

import com.nativenavs.user.model.User;

public interface UserService {
    public void signUp(User user);

    public boolean checkDuplicatedEmail(String email);

    public User searchUser(String email);
}

package com.nativenavs.user.mapper;

import com.nativenavs.user.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public void signUp(User user);
    public boolean checkDuplicatedEmail(String email);
    public User searchUser(String email);
}

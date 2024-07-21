package com.nativenavs.user.mapper;

import com.nativenavs.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public void signUp(User user);
    public boolean checkDuplicatedEmail(String email);
    public User search(String email);
    public List<User> searchAll();
}

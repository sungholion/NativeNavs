package com.nativenavs.user.mapper;

import com.nativenavs.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public void signUp(User user);
    public boolean checkDuplicatedEmail(String email);
    public User searchOneUser(String email);
    public List<User> searchAllUser();
    public User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}

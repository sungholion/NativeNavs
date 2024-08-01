package com.nativenavs.user.mapper;

import com.nativenavs.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    public boolean checkDuplicatedEmail(String email);
    public boolean checkDuplicatedNickname(String nickname);

    public void signUp(User user);

    public List<User> searchAllUser();
    public User searchByEmail(String email);
    public User searchById(int id);
    public User searchByNickname(String nickname);
    public User searchByName(String name);

    public void updateUser(@Param("existingId") int existingId, @Param("updateUser") User updateUser);
    public void deleteUser(int id);
    void insertUserLanguage(@Param("userId") int userId, @Param("languageId") int languageId);
}

package com.nativenavs.auth.mapper;

import com.nativenavs.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    public User loginByEmail(@Param("email") String email, @Param("password") String password);
}

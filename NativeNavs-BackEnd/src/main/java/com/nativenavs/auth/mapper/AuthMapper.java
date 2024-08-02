package com.nativenavs.auth.mapper;

import com.nativenavs.user.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    public UserDTO loginByEmail(@Param("email") String email, @Param("password") String password);
}

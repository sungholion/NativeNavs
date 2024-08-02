package com.nativenavs.user.service;

import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;

import java.util.List;

public interface UserService {
    public boolean checkDuplicatedEmail(String email);
    public boolean checkDuplicatedNickname(String nickname);

    public void signUp(UserDTO userDTO);
    public void updateUser(int existingId, UserDTO updateUserDTO);
    public void updateUserDTOFields(UserEntity updateUserEntity, UserDTO updateUserDTO);
    public void deleteUser(int id);

    public List<UserDTO> searchAllUser();
    public UserDTO searchByEmail(String email);
    public UserDTO searchById(int id);
    public UserDTO searchByNickname(String nickname);
    public UserDTO searchByName(String name);

    public void addAuthenticatedUser(String email);
}

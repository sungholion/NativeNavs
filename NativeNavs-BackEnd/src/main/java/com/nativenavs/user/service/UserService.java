package com.nativenavs.user.service;

import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.dto.UserSearchDTO;
import com.nativenavs.user.entity.UserEntity;

import java.util.List;

public interface UserService {
    public boolean checkDuplicatedEmail(String email);
    public boolean checkDuplicatedNickname(String nickname);

    public void addAuthenticatedUser(String email);
    public void signUp(UserDTO userDTO);

    public void updateUser(int existingId, UserDTO updateUserDTO);
    public void updateUserDTOFields(UserEntity updateUserEntity, UserDTO updateUserDTO);
    public void deleteUser(int id);

    public List<UserSearchDTO> searchAllUser();
    public UserSearchDTO searchById(int id);
    public UserDTO searchByEmail(String email);
    public UserSearchDTO searchByEmailForClient(String email);
    public UserSearchDTO searchByName(String name);
    public UserSearchDTO searchByNickname(String nickname);

    public int changeEmailToId(String email);
}

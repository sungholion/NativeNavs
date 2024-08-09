package com.nativenavs.user.service;

import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.dto.UserSearchDTO;
import com.nativenavs.user.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    public boolean checkDuplicatedEmail(String email);
    public boolean checkDuplicatedNickname(String nickname);

    public void addAuthenticatedUser(String email);
    public void signUp(UserDTO userDTO, MultipartFile profileImage);

    public void updateUser(int existingId, UserDTO updateUserDTO, MultipartFile profileImage);
    public void updateUserDTOFields(UserEntity updateUserEntity, UserDTO updateUserDTO,MultipartFile profileImage);
    public void deleteUser(int id);

    public List<UserSearchDTO> searchAllUser();
    public UserSearchDTO searchById(int id);
    public UserDTO searchByEmail(String email);
    public UserSearchDTO searchByEmailForClient(String email);
    public UserSearchDTO searchByName(String name);
    public UserSearchDTO searchByNickname(String nickname);

    public int changeEmailToId(String email);

    public void updateFcmToken(int userId, String fcmToken);
}

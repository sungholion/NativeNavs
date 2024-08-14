package com.nativenavs.auth.service;

import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nativenavs.user.dto.UserDTO.toUserDTO;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    public UserDTO loginByEmail(String email, String password, String device) {
        Optional<UserEntity> loginUserEntity = userRepository.findByEmailAndPassword(email, password);

        if (loginUserEntity.isPresent()) {
            UserEntity userEntity = loginUserEntity.get();
            userEntity.setDevice(device);
            return toUserDTO(userEntity);
        } else {
            throw new RuntimeException("Invalid email or password");
        }

    }


}

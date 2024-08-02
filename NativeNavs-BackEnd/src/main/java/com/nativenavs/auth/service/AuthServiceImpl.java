package com.nativenavs.auth.service;

import com.nativenavs.auth.mapper.AuthMapper;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nativenavs.user.dto.UserDTO.toUserDTO;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    // 이메일 로그인 로직
    public UserDTO loginByEmail(String email, String password, String device) {
        Optional<UserEntity> loginUserEntity = userRepository.findByEmailAndPassword(email, password);

        if (loginUserEntity.isPresent()) {
            UserEntity userEntity = loginUserEntity.get();
            userEntity.setDevice(device);
            // UserEntity를 UserDTO로 변환하는 로직 필요
            return toUserDTO(userEntity);
        } else {
            // 사용자 정보가 없을 때의 처리 (예: 예외 던지기)
            throw new RuntimeException("Invalid email or password");
        }

    }

}

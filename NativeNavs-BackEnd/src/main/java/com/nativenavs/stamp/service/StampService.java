package com.nativenavs.stamp.service;

import com.nativenavs.stamp.dto.StampDTO;
import com.nativenavs.stamp.entity.UserStampEntity;
import com.nativenavs.stamp.repository.UserStampRepository;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StampService {

    private final UserRepository userRepository;
    private final UserStampRepository userStampRepository;

    @Transactional
    public List<StampDTO> getAllStampsByUserId(int userId) {
        // 유저가 존재하는지 확인
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 유저가 가진 스탬프 리스트 조회
        List<UserStampEntity> userStamps = userStampRepository.findAllByUser(user);

        // UserStampEntity 리스트를 StampDTO 리스트로 변환
        return userStamps.stream()
                .map(StampDTO::toStampDTO)
                .collect(Collectors.toList());
    }
}

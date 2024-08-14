package com.nativenavs.user.service;

import com.nativenavs.common.service.AwsS3ObjectStorage;
import com.nativenavs.stamp.service.StampService;
import com.nativenavs.user.dto.UserDTO;
import com.nativenavs.user.dto.UserSearchDTO;
import com.nativenavs.user.entity.UserEntity;
import com.nativenavs.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final StampService userStampService;
    @Autowired
    private UserRepository userRepository;

    private final Set<String> authenticatedUsers = ConcurrentHashMap.newKeySet();   // 인증 회원을 임시 저장
    @Autowired
    private AwsS3ObjectStorage awsS3ObjectStorageUpload;

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public boolean checkDuplicatedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean checkDuplicatedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void addAuthenticatedUser(String email){
        authenticatedUsers.add(email);
    }

    @Override
    public void signUp(UserDTO userDTO, MultipartFile profileFile) {
        if(!authenticatedUsers.contains(userDTO.getEmail())){  // 이메일 미인증시
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }

        UserEntity userEntity = UserEntity.toSaveEntity(userDTO);
        String imageUrl =  awsS3ObjectStorageUpload.uploadFile(profileFile);
        userEntity.setImage(imageUrl);

        userRepository.save(userEntity);
        authenticatedUsers.remove(userDTO.getEmail()); // 메모리 저장소에서 인증 회원 제거 (가입 완료했으니)

//        userStampService.addStamp(1, userEntity.getId());
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public List<UserSearchDTO> searchAllUser() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(UserSearchDTO::toUserSearchDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserSearchDTO searchById(int id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return UserSearchDTO.toUserSearchDTO(userEntity);
    }

    @Override
    public UserDTO searchByEmail(String email) {    // 로그인이나 내부에서 필요할 때 password를 포함하여 반환
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
        return UserDTO.toUserDTO(userEntity);
    }

    public UserSearchDTO searchByEmailForClient(String email){  // 사용자가 조회 시, password를 빼고 반환
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
        return UserSearchDTO.toUserSearchDTO(userEntity);
    }

    @Override
    public UserSearchDTO searchByName(String name) {
        UserEntity userEntity = userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("User with name " + name + " not found"));
        return UserSearchDTO.toUserSearchDTO(userEntity);
    }

    @Override
    public UserSearchDTO searchByNickname(String nickname) {
        UserEntity userEntity = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException("User with nickname " + nickname + " not found"));
        return UserSearchDTO.toUserSearchDTO(userEntity);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Transactional
    @Override
    public void updateUser(int existingId, UserDTO updateUserDTO, MultipartFile profileImage) {

        Optional<UserEntity> findUserEntity = userRepository.findById(existingId);
        if (findUserEntity.isPresent()) {
            UserEntity updateUserEntity = findUserEntity.get();
            userRepository.save(updateUserDTOFields(updateUserEntity, updateUserDTO, profileImage));
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    @Transactional
    public UserEntity updateUserDTOFields(UserEntity updateUserEntity, UserDTO updateUserDTO,MultipartFile profileImage) {
        if(updateUserEntity.getImage()!= null &&!updateUserDTO.getImage().isEmpty()){
            awsS3ObjectStorageUpload.deleteFile(updateUserEntity.getImage());
            String imageUrl =  awsS3ObjectStorageUpload.uploadFile(profileImage);
            updateUserEntity.setImage(imageUrl);
        }


        //새로 들어오는 정보가 없다면
        if (updateUserDTO.getNickname() == null ) {
            updateUserEntity.setNickname(updateUserEntity.getNickname());
        } else {
            updateUserEntity.setNickname(updateUserDTO.getNickname());
        }

        if(updateUserDTO.getPassword() == null) {
            updateUserEntity.setPassword(updateUserEntity.getPassword());
        } else {
            updateUserEntity.setPassword(updateUserDTO.getPassword());
        }

        if(updateUserDTO.getUserLanguage() == null) {
            updateUserEntity.setUserLanguage(updateUserEntity.getUserLanguage());
        } else {
            updateUserEntity.setUserLanguage(updateUserDTO.getUserLanguage());
        }

        if(updateUserDTO.getPhone() == null) {
            updateUserEntity.setPhone(updateUserEntity.getPhone());
        } else {
            updateUserEntity.setPhone(updateUserDTO.getPhone());
        }

        return updateUserEntity;

    }

    @Override
    public void deleteUser(int id) {
        Optional<UserEntity> findUserEntity = userRepository.findById(id);
        userRepository.delete(findUserEntity.get());
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public int changeEmailToId(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));
        return userEntity.getId();
    }

    // ----

    @Transactional
    @Override
    public void updateFcmToken(int userId, String fcmToken) {
        userRepository.updateFcmToken(userId, fcmToken);
    }



}

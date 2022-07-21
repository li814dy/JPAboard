package com.JPAboard.service;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.domain.repository.FileRepository;
import com.JPAboard.domain.repository.UserRepository;
import com.JPAboard.dto.UserRequestDTO;
import com.JPAboard.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public UserEntity checkUser(String uid) {
        UserEntity userEntity = userRepository.checkUserInfo(uid);

        return userEntity;
    }

    @Transactional
    public Long joinUser(UserRequestDTO userRequestDTO) {
        return userRepository.save(userRequestDTO.toEntity()).getUserNum();
    }

    @Transactional
    public UserEntity loginUser(String uid, String upw) {
        UserEntity userEntity = userRepository.selectUserInfo(uid, upw);

        return userEntity;
    }

    @Transactional
    public UserResponseDTO loginUserDTO(String uid, String upw) {
        UserEntity userEntity = userRepository.selectUserInfo(uid, upw);
        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }

    @Transactional
    public UserEntity findPW(String uname, String uid) {
        UserEntity userEntity = userRepository.findUserPw(uname, uid);

        return userEntity;
    }

    @Transactional
    public UserResponseDTO getUser(Long userNum) {
        Optional<UserEntity> userEntityWrapper = userRepository.findById(userNum);
        UserEntity userEntity = userEntityWrapper.get();

        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }

    @Transactional
    public UserResponseDTO checkUserId(String uid) {
        UserEntity userEntity = userRepository.checkUserInfo(uid);
        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }

    @Transactional
    public void deleteUser(Long no) {
        userRepository.deleteById(no);
    }
}

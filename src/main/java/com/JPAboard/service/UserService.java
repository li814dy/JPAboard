package com.JPAboard.service;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.domain.repository.UserRepository;
import com.JPAboard.dto.UserRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public UserEntity userJoinCheck(String uid) {
        UserEntity userEntity = userRepository.checkUserInfo(uid);

        return userEntity;
    }

/*    @Transactional
    public UserResponseDTO userJoinCheck(String uid) {
        Optional<UserEntity> userEntityWraper = userRepository.checkUserInfo(uid);
        UserEntity userEntity = userEntityWraper.get();

        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }*/

    @Transactional
    public Long userJoin(UserRequestDTO userRequestDTO) {
        return userRepository.save(userRequestDTO.toEntity()).getUserNum();
    }

    @Transactional
    public UserEntity userLogin(String uid, String upw) {
        UserEntity userEntity = userRepository.selectUserInfo(uid, upw);

        return userEntity;
    }

/*    @Transactional
    public UserResponseDTO userLogin(String uid, String upw) {
        Optional<UserEntity> userEntityWraper = userRepository.selectUserInfo(uid, upw);
        UserEntity userEntity = userEntityWraper.get();

        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }*/

    @Transactional
    public UserEntity findPW(String uname, String uid) {
        UserEntity userEntity = userRepository.findUserPw(uname, uid);

        return userEntity;
    }

/*    @Transactional
    public UserResponseDTO findPW(String uname, String uid) {
        Optional<UserEntity> userEntityWraper = userRepository.findUserPw(uname, uid);
        UserEntity userEntity = userEntityWraper.get();

        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }*/

/*    @Transactional
    public void userDelete(Long id) {
        userRepository.deleteById(id);
    }*/
}

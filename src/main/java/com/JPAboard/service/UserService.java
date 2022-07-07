package com.JPAboard.service;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.domain.repository.UserRepository;
import com.JPAboard.dto.BoardDto;
import com.JPAboard.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public Long userJoin(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getUserNum();
    }

    @Transactional
    public UserEntity userLogin(String uid, String upw) {
        UserEntity userEntity = userRepository.selectUserInfo(uid, upw);

        return userEntity;
    }

    @Transactional
    public UserEntity findPW(String uname, String uid) {
        UserEntity userEntity = userRepository.findUserPw(uname, uid);

        return userEntity;
    }

/*    @Transactional
    public void userDelete(Long id) {
        userRepository.deleteById(id);
    }*/
}

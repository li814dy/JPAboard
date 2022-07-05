package com.JPAboard.service;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.domain.repository.UserRepository;
import com.JPAboard.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    @Transactional
    public UserEntity userLogin(UserEntity userEntity) {
        return userRepository.userLogin(userEntity);
    }

    @Transactional
    public Long userJoin(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getUserNum();
    }

    //@Transactional
    //public void userDelete(Long id) {
    //    userRepository.deleteById(id);
    //}
}

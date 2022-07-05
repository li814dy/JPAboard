package com.JPAboard.service;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public UserEntity userLogin(UserEntity userEntity) {
        return userRepository.userLogin(userEntity);
    }

    public void userJoin(UserEntity userEntity) {
        //ue.setRole("User");
        userRepository.save(userEntity);
    }
}

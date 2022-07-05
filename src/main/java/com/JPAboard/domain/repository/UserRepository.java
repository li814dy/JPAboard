package com.JPAboard.domain.repository;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    public UserDto userLogin(UserDto userDto);
}
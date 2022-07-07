package com.JPAboard.domain.repository;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query("SELECT u from UserEntity u where u.userId=:userId and u.userPw=:userPw")
    UserEntity selectUserInfo(@Param("userId")String userId, @Param("userPw")String userPw);

    @Query("SELECT u from UserEntity u where u.userName=:userName and u.userId=:userId")
    UserEntity findUserPw(@Param("userName")String userName, @Param("userId")String userId);
}
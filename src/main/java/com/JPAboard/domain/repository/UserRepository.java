package com.JPAboard.domain.repository;

import com.JPAboard.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    public UserEntity userLogin(UserEntity userEntity);
}
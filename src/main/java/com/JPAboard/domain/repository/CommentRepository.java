package com.JPAboard.domain.repository;

import com.JPAboard.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>  {
    @Query("SELECT u from CommentEntity u where u.id=:id and u.password=:password")
    Optional<CommentEntity> findByNumPw(@Param("id")Long id, @Param("password")String password);
}
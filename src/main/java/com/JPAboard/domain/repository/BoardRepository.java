package com.JPAboard.domain.repository;

import com.JPAboard.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 글제목 기준 검색 설정
    List<BoardEntity> findByTitleContaining(String keyword);
}
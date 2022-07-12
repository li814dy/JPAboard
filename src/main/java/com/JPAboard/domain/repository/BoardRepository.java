package com.JPAboard.domain.repository;

import com.JPAboard.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 글제목 기준 검색 설정
    List<BoardEntity> findByTitleContaining(String keyword);

    @Query("SELECT u from BoardEntity u where u.id=:id")
    BoardEntity findByBoardId(@Param("id")String id);
}
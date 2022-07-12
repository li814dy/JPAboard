package com.JPAboard.service;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import com.JPAboard.domain.repository.BoardRepository;
import com.JPAboard.domain.repository.CommentRepository;
import com.JPAboard.dto.CommentRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class CommentService {
    private BoardRepository boardRepository;
    private CommentRepository commentRepository;

    @Transactional
    public Long saveComment(Long no, CommentRequestDTO commentRequestDTO) {
        BoardEntity boardEntity = boardRepository.findById(no).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않음"));

        commentRequestDTO.setBoard(boardEntity);

        CommentEntity commentEntity = commentRequestDTO.toEntity();
        commentRepository.save(commentEntity);

        return commentRequestDTO.toEntity().getId();
    }
}

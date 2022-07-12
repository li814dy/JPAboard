package com.JPAboard.service;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import com.JPAboard.domain.repository.BoardRepository;
import com.JPAboard.domain.repository.CommentRepository;
import com.JPAboard.dto.BoardDTO;
import com.JPAboard.dto.CommentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class CommentService {
    private BoardRepository boardRepository;
    private CommentRepository commentRepository;

/*    @Transactional
    public Long saveComment(Long no, CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(no).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않음"));

        commentDTO.setBoard(boardEntity);

        CommentEntity commentEntity = commentDTO.toEntity();
        commentRepository.save(commentEntity);

        return commentDTO.getId();
    }*/

    @Transactional
    public Long saveComment(Long no, CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(no).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않음"));

        commentDTO.setBoard(boardEntity);

        return commentRepository.save(commentDTO.toEntity()).getId();
    }
}

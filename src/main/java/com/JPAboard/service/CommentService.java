package com.JPAboard.service;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import com.JPAboard.domain.repository.BoardRepository;
import com.JPAboard.domain.repository.CommentRepository;
import com.JPAboard.dto.CommentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class CommentService {
    private BoardRepository boardRepository;
    private CommentRepository commentRepository;

    @Transactional
    public Long saveComment(Long no, CommentDTO commentDTO, CommentEntity commentEntity) {
        String postNo = no.toString();
        BoardEntity boardEntity = boardRepository.findByBoardId(postNo);

        commentDTO.setId(commentEntity.getId());
        commentDTO.setWriter(commentEntity.getWriter());
        commentDTO.setPassword(commentEntity.getPassword());
        commentDTO.setContent(commentEntity.getContent());
        commentDTO.setCreatedDate(commentEntity.getCreatedDate());
        commentDTO.setModifiedDate(commentEntity.getModifiedDate());
        commentDTO.setPost(boardEntity);

        Long l = commentRepository.save(commentDTO.builder().build().toEntity()).getId();

        return l;
    }
}

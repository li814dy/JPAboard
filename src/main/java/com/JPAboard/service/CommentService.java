package com.JPAboard.service;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import com.JPAboard.domain.repository.BoardRepository;
import com.JPAboard.domain.repository.CommentRepository;
import com.JPAboard.domain.repository.UserRepository;
import com.JPAboard.dto.CommentRequestDTO;
import com.JPAboard.dto.CommentResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {
    private BoardRepository boardRepository;
    private UserRepository userRepository;
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

    public CommentResponseDTO getComment(Long id) {
        Optional<CommentEntity> commentEntityWraper = commentRepository.findById(id);
        CommentEntity commentEntity = commentEntityWraper.get();

        CommentResponseDTO commentResponseDTO = new CommentResponseDTO(commentEntity);

        return commentResponseDTO;
    }

    public CommentResponseDTO getCommentPw(Long id, String password) {
        Optional<CommentEntity> commentEntityWraper = commentRepository.findByNumPw(id, password);
        CommentEntity commentEntity = commentEntityWraper.get();

        CommentResponseDTO commentResponseDTO = new CommentResponseDTO(commentEntity);

        return commentResponseDTO;
    }

    public void deleteComment(Long cno) {
        boardRepository.deleteById(cno);
        userRepository.deleteById(cno);
        commentRepository.deleteById(cno);
    }
}

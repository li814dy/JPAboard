package com.JPAboard.dto;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@ToString
@NoArgsConstructor
public class CommentRequestDTO {
    private Long id;
    private String writer;
    private String password;
    private String content;
    private BoardEntity board;

    // DTO to Entity
    public CommentEntity toEntity(){
        CommentEntity commentEntity = CommentEntity.builder()
                .id(id)
                .writer(writer)
                .password(password)
                .content(content)
                .board(board)
                .build();
        return commentEntity;
    }
}
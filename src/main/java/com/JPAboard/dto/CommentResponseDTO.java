package com.JPAboard.dto;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String writer;
    private String password;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private Long boardId;

    // Entity to DTO
    @Builder
    public CommentResponseDTO(CommentEntity commentEntity) {
        this.id = commentEntity.getId();
        this.writer = commentEntity.getWriter();
        this.password = commentEntity.getPassword();
        this.content = commentEntity.getContent();
        this.createdDate = commentEntity.getCreatedDate();
        this.modifiedDate = commentEntity.getModifiedDate();
        this.boardId = commentEntity.getBoard().getId();
    }
}
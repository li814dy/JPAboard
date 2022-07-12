package com.JPAboard.dto;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String writer;
    private String password;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
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

    // Entity to DTO
    @Builder
    public CommentDTO(Long id, String writer, String password, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, BoardEntity board) {
        this.id = id;
        this.writer = writer;
        this.password = password;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.board = board;
    }
}
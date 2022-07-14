package com.JPAboard.dto;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@ToString
@NoArgsConstructor
public class BoardRequestDTO {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private Long fileId;
    private List<CommentEntity> comments;

    // DTO to Entity
    public BoardEntity toEntity() {
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .fileId(fileId)
                .comments(comments)
                .build();
        return boardEntity;
    }
}
package com.JPAboard.dto;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
public class BoardResponseDTO {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private Long fileId;
/*    private Long[] files;*/
    private List<CommentResponseDTO> comments;

    // Entity to DTO
    @Builder
    public BoardResponseDTO(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.writer = boardEntity.getWriter();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.fileId = boardEntity.getFileId();
        this.createdDate = boardEntity.getCreatedDate();
        this.modifiedDate = boardEntity.getModifiedDate();
        this.comments = boardEntity.getComments().stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }
}
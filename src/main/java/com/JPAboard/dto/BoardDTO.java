package com.JPAboard.dto;

import com.JPAboard.domain.entity.BoardEntity;
import com.JPAboard.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    /*private List<CommentDTO> commentDTOs;
    private List<CommentEntity> comments = comment(commentDTOs);
    private List<CommentEntity> comment(List<CommentDTO> commentDTO) {
        return commentDTO.stream()
                .map(CommentDTO::toEntity)
                .collect(Collectors.toList());
    }*/
//    private List<CommentDTO> comments;

/*    private List<CommentDTO> commentDTO;
    private List<CommentEntity> comments = commentDTO.stream().map(CommentDTO::toEntity).collect(Collectors.toList());*/
    private List<CommentEntity> comments;

    // DTO to Entity
    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .comments(comments)
                .build();
        return boardEntity;
    }

    // Entity to DTO
/*    @Builder
    public BoardDTO(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate, List<CommentEntity> comments) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
    }*/
/*    @Builder
    public BoardDTO(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate, List<CommentDTO> comments) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.commentDTO = comments;
    }*/
    @Builder
    public BoardDTO(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate, List<CommentEntity> comments) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
    }
}
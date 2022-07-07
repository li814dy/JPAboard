package com.JPAboard.dto;

import com.JPAboard.domain.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<CommentDTO> comments;
    /*private List<CommentDto> comments(BoardEntity entity) {
        return entity.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
    };*/

    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                //.comments(comments)
                //.comments(comments(boardEntity))
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDTO(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate, List<CommentDTO> comments) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
    }
}
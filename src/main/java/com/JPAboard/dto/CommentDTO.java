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
    private BoardEntity post;

    // DTO to Entity
    public CommentEntity toEntity(){
        CommentEntity commentEntity = CommentEntity.builder()
                .id(id)
                .writer(writer)
                .password(password)
                .content(content)
                .post(post)
                .build();
        return commentEntity;
    }

    // Entity to DTO
    @Builder
    public CommentDTO(Long id, String writer, String password, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, BoardEntity post) {
        this.id = id;
        this.writer = writer;
        this.password = password;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.post = post;
    }
}
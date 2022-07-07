package com.JPAboard.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "board")
public class BoardEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String writer;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "boardEntity", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    /*private List<CommentEntity> comments;*/
    private List<CommentEntity> comments = new ArrayList<>();

    // 생성자
    @Builder
    public BoardEntity(Long id, String title, String content, String writer, List<CommentEntity> comments) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.comments = comments;
    }
}
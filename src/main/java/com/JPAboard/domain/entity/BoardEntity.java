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

    @ElementCollection
    @CollectionTable(name = "files")
    @Column
    private List<Long> files;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;

    // 생성자
    @Builder
    public BoardEntity(Long id, String title, String content, String writer, List<Long> files, List<CommentEntity> comments) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.files = files;
        this.comments = comments;
    }
}
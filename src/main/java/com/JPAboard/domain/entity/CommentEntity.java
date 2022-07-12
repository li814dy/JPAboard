package com.JPAboard.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String writer;

    @Column(length = 10, nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 게시글
    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;

    /*
    // 작성자
    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserEntity user;
    */

    @Builder
    public CommentEntity(Long id, String writer, String password, String content, BoardEntity board) {
        this.id = id;
        this.writer = writer;
        this.password = password;
        this.content = content;
        this.board = board;
    }
}
package com.JPAboard.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class UserEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userNum;

    @Column(length = 10, nullable = false)
    private String userId;

    @Column(length = 20, nullable = false)
    private String userPw;

    @Column(length = 30, nullable = false)
    private String userName;

    @Column
    private Long fileId;

    @Builder
    public UserEntity(Long userNum, String userId, String userPw, String userName, Long fileId) {
        this.userNum = userNum;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.fileId = fileId;
    }
}
package com.JPAboard.dto;

import com.JPAboard.domain.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseDTO {
    private Long userNum;
    private String userId;
    private String userPw;
    private String userName;
    private String createdDate;
    private String modifiedDate;
    private Long fileId;

    // Entity to DTO
    @Builder
    public UserResponseDTO(UserEntity userEntity) {
        this.userNum = userEntity.getUserNum();
        this.userId = userEntity.getUserId();
        this.userPw = userEntity.getUserPw();
        this.userName = userEntity.getUserName();
        this.createdDate = userEntity.getCreatedDate();
        this.modifiedDate = userEntity.getModifiedDate();
        this.fileId = userEntity.getFileId();
    }
}
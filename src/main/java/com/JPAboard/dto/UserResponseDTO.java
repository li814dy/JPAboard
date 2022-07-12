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
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // DTO to Entity
    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .userNum(userNum)
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .build();
        return userEntity;
    }

    // Entity to DTO
    @Builder
    public UserResponseDTO(Long userNum, String userId, String userPw, String userName, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.userNum = userNum;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
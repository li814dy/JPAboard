package com.JPAboard.dto;

import com.JPAboard.domain.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRequestDTO {
    private Long userNum;
    private String userId;
    private String userPw;
    private String userName;

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
}
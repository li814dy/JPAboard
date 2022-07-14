package com.JPAboard.dto;

import com.JPAboard.domain.entity.FileEntity;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@NoArgsConstructor
public class FileRequestDTO {
    private Long fileId;
    private String fileName;
    private String fileType;
    private String fileSize;
    private String filePath;

    public FileEntity toEntity() {
        FileEntity fileEntity = FileEntity.builder()
                .fileId(fileId)
                .fileName(fileName)
                .fileType(fileType)
                .fileSize(fileSize)
                .filePath(filePath)
                .build();
        return fileEntity;
    }
}

package com.JPAboard.dto;

import com.JPAboard.domain.entity.FileEntity;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class FileResponseDTO {
    private Long fileId;
    private String fileName;
    private String fileType;
    private String fileSize;
    private String filePath;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public FileResponseDTO (FileEntity fileEntity) {
        this.fileId = fileEntity.getFileId();
        this.fileName = fileEntity.getFileName();
        this.fileType = fileEntity.getFileType();
        this.fileSize = fileEntity.getFileSize();
        this.filePath = fileEntity.getFilePath();
        this.createdDate = fileEntity.getCreatedDate();
        this.modifiedDate = fileEntity.getModifiedDate();
    }
}
package com.JPAboard.service;

import com.JPAboard.domain.entity.FileEntity;
import com.JPAboard.domain.repository.FileRepository;
import com.JPAboard.dto.FileRequestDTO;
import com.JPAboard.dto.FileResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FileService {
    private FileRepository fileRepository;

    @Transactional
    public Long saveFile(FileRequestDTO fileRequestDTO) {
        return fileRepository.save(fileRequestDTO.toEntity()).getFileId();
    }

    @Transactional
    public FileResponseDTO getFile(Long fileId) {
        Optional<FileEntity> fileEntityWrapper = fileRepository.findById(fileId);
        FileEntity fileEntity = fileEntityWrapper.get();

        FileResponseDTO fileResponseDTO = new FileResponseDTO(fileEntity);

        return fileResponseDTO;
    }
}

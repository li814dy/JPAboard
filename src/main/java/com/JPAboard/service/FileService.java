package com.JPAboard.service;

import com.JPAboard.domain.entity.FileEntity;
import com.JPAboard.domain.repository.FileRepository;
import com.JPAboard.dto.FileRequestDTO;
import com.JPAboard.dto.FileResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    public List<FileResponseDTO> getFiles(List<Long> fileId) {
        List<FileResponseDTO> fileResponseDTOs = new ArrayList<>();

        for (Long fileIds : fileId) {
            Optional<FileEntity> fileEntityWrapper = fileRepository.findById(fileIds);
            FileEntity fileEntity = fileEntityWrapper.get();

            FileResponseDTO fileResponseDTO = new FileResponseDTO(fileEntity);
            fileResponseDTOs.add(fileResponseDTO);
        }

        return fileResponseDTOs;
    }

    public ResponseEntity<byte[]> displayImg(String filePath) throws IOException {
        InputStream imgStream = new FileInputStream(filePath);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read;
        byte[] imgByteArray = new byte[imgStream.available()];
        while ((read = imgStream.read(imgByteArray, 0, imgByteArray.length)) != -1) {
            buffer.write(imgByteArray, 0, read);
        } buffer.flush();
        byte[] imgArray = buffer.toByteArray();
        imgStream.close();

        return new ResponseEntity<byte[]>(imgArray, HttpStatus.OK);
    }
}

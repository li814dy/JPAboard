package com.JPAboard.controller;

import com.JPAboard.dto.FileResponseDTO;
import com.JPAboard.dto.UserResponseDTO;
import com.JPAboard.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
@Controller
public class FileController {
    private FileService fileService;

    // 파일 다운로드 설정
    @GetMapping("/post/{id}/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("id") Long id, @PathVariable("fileId") Long fileId) throws IOException {
        FileResponseDTO fileResponseDTO = fileService.getFile(fileId);
        Path path = Paths.get(fileResponseDTO.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileResponseDTO.getFileName(), "UTF-8") + "\"").body(resource);
    }

    // 파일 디스플레이 설정
    @GetMapping("/file/{fileId}")
    public ResponseEntity<byte[]> fileDisplay(@PathVariable("fileId") Long fileId) throws IOException {
        FileResponseDTO fileResponseDTO = fileService.getFile(fileId);
        String fileName = fileResponseDTO.getFileName();

        if (fileName.contains(".jpg") || fileName.contains(".JPG") || fileName.contains(".jpeg") || fileName.contains(".JPEG") || fileName.contains(".png") || fileName.contains(".PNG") | fileName.contains(".gif") || fileName.contains(".GIF")) {
            return fileService.displayImg(fileResponseDTO.getFilePath());
        } else {
            String filePath = System.getProperty("user.dir") + "\\" + "file.png";

            return fileService.displayImg(filePath);
        }
    }

/*    @GetMapping("/file/user/{fileId}")
    public ResponseEntity<byte[]> imgDisplay(@PathVariable("fileId") Long fileId) throws IOException {
        FileResponseDTO fileResponseDTO = fileService.getFile(fileId);

        if (fileResponseDTO != null) {
            return fileService.displayImg(fileResponseDTO.getFilePath());
        } else {
            String filePath = System.getProperty("user.dir") + "\\" + "user.png";

            return fileService.displayImg(filePath);
        }
    }*/

    @GetMapping("/file/user/{fileId}")
    public ResponseEntity<byte[]> imgDisplay(@PathVariable("fileId") Long fileId, HttpServletRequest request) throws IOException {
        FileResponseDTO fileResponseDTO = fileService.getFile(fileId);

        if (fileResponseDTO != null) {
            return fileService.displayImg(fileResponseDTO.getFilePath());
        } else {
            String filePath = System.getProperty("user.dir") + "\\" + "user.png";

            return fileService.displayImg(filePath);
        }
    }

    @GetMapping("/file/null")
    public ResponseEntity<byte[]> nullDisplay() throws IOException {
        String filePath = System.getProperty("user.dir") + "\\" + "user.png";

        return fileService.displayImg(filePath);
    }
}
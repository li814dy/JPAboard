package com.JPAboard.controller;

import com.JPAboard.dto.*;
import com.JPAboard.service.BoardService;
import com.JPAboard.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
@Controller
public class BoardController {
    private BoardService boardService;
    private FileService fileService;

    // 게시글 목록
    @GetMapping("/")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardResponseDTO> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);
        model.addAttribute("boardList", boardList);         // 게시글 목록 출력
        model.addAttribute("pageList", pageList);           // 게시글 페이징

        return "board/list";
    }

    @GetMapping("/post/write")
    public String write() {
        return "board/write";
    }

/*    @PostMapping("/post/write")
    public String write(BoardRequestDTO boardRequestDTO) {
        boardService.savePost(boardRequestDTO);

        return "redirect:/";
    }*/

    @PostMapping("/post/write")
    public String write(@RequestParam(value = "file", required = false) MultipartFile multipartFile, BoardRequestDTO boardRequestDTO) {
        if(!multipartFile.isEmpty()) {
            try {
                String fileName = multipartFile.getOriginalFilename();
                String fileType = StringUtils.getFilenameExtension(fileName);
                String fileSize = Long.toString(multipartFile.getSize());

                String makePath = System.getProperty("user.dir") + "\\files";

                if (!ObjectUtils.isEmpty(fileType)) {
                    if (!new File(makePath).exists()) {
                        try {
                            new File(makePath).mkdir();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                }

                String filePath = makePath + "\\" + fileName;
                multipartFile.transferTo(new File(filePath));

                FileRequestDTO fileRequestDTO = new FileRequestDTO();
                fileRequestDTO.setFileName(fileName);
                fileRequestDTO.setFilePath(filePath);
                fileRequestDTO.setFileType(fileType);
                fileRequestDTO.setFileSize(fileSize);

                Long fileId = fileService.saveFile(fileRequestDTO);
                boardRequestDTO.setFileId(fileId);
                boardService.savePost(boardRequestDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardResponseDTO boardResponseDTO = boardService.getPost(no);
        FileResponseDTO fileResponseDTO = fileService.getFile(boardResponseDTO.getFileId());
        List<CommentResponseDTO> comments = boardResponseDTO.getComments();

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("commentList", comments);
        }
        model.addAttribute("board", boardResponseDTO);
        model.addAttribute("file", fileResponseDTO);

        return "board/detail";
    }

    @GetMapping("/post/{no}/edit")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardResponseDTO boardResponseDTO = boardService.getPost(no);
        FileResponseDTO fileResponseDTO = fileService.getFile(boardResponseDTO.getFileId());

        model.addAttribute("board", boardResponseDTO);
        model.addAttribute("file", fileResponseDTO);

        return "board/update";
    }

    @PutMapping("/post/{no}/edit")
    public String update(BoardRequestDTO boardRequestDTO) {
        boardService.savePost(boardRequestDTO);

        return "redirect:/";
    }

    // 유저 데이터 정보 포함 게시글 삭제 구현 필요
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/";
    }

    // 글제목 기준 검색 설정
    @GetMapping("/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardResponseDTO> boardDTOList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardDTOList);

        return "board/list";
    }
}

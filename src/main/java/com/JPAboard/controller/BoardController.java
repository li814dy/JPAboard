package com.JPAboard.controller;

import com.JPAboard.dto.*;
import com.JPAboard.service.BoardService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
public class BoardController {
    private BoardService boardService;
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

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

    @PostMapping("/post/write")
    public String write(BoardRequestDTO boardRequestDTO) {
        boardService.savePost(boardRequestDTO);

        return "redirect:/";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardResponseDTO boardResponseDTO = boardService.getPost(no);
        List<CommentResponseDTO> comments = boardResponseDTO.getComments();

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("commentList", comments);
        }
        model.addAttribute("board", boardResponseDTO);

        return "board/detail";
    }

    @GetMapping("/post/{no}/edit")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardResponseDTO boardResponseDTO = boardService.getPost(no);
        model.addAttribute("board", boardResponseDTO);

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

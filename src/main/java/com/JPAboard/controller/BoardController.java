package com.JPAboard.controller;

import com.JPAboard.domain.entity.CommentEntity;
import com.JPAboard.dto.BoardDTO;
import com.JPAboard.dto.CommentDTO;
import com.JPAboard.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
public class BoardController {
    private BoardService boardService;

    // 게시글 목록
    @GetMapping("/")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDTO> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);
        model.addAttribute("boardList", boardList);         // 게시글 목록 출력
        model.addAttribute("pageList", pageList);           // 게시글 페이징

        return "board/list";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write";
    }

    @PostMapping("/post")
    public String write(BoardDTO boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/";
    }
    
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BoardDTO boardDTO = boardService.getPost(no);
        List<CommentEntity> comments = boardDTO.getComments();

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        model.addAttribute("boardDto", boardDTO);

        return "board/detail";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDTO boardDTO = boardService.getPost(no);
        model.addAttribute("boardDto", boardDTO);

        return "board/update";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BoardDTO boardDTO) {
        boardService.savePost(boardDTO);

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
        List<BoardDTO> boardDTOList = boardService.searchPosts(keyword);
        model.addAttribute("boardList", boardDTOList);

        return "board/list";
    }
}

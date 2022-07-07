package com.JPAboard.controller;

import com.JPAboard.domain.entity.CommentEntity;
import com.JPAboard.dto.CommentDTO;
import com.JPAboard.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
public class CommentController {
    private CommentService commentService;

    @GetMapping("/post/{no}/comments")
    public String write() {
        return "board/detail";
    }

    @PostMapping("/post/{no}/comments")
    public String write(@PathVariable("no") Long no, CommentDTO commentDTO) {
        CommentEntity commentEntity = commentDTO.builder().build().toEntity();
        commentService.saveComment(no, commentDTO, commentEntity);
        return "board/detail";
    }
}

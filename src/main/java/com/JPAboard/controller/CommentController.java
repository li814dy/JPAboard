package com.JPAboard.controller;

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
    public String write(@PathVariable Long no, CommentDTO commentDTO) {
        commentService.saveComment(no, commentDTO);

        return "board/detail";
    }
}

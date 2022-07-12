package com.JPAboard.controller;

import com.JPAboard.dto.CommentRequestDTO;
import com.JPAboard.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
public class CommentController {
    private CommentService commentService;

    @GetMapping("/post/{no}/comments")
    public String write(@PathVariable("no") Long no) {
        return "/post/"+no+"/comments";
    }

    @PostMapping("/post/{no}/comments")
    public String write(@PathVariable("no") Long no, CommentRequestDTO commentRequestDTO) {
        commentService.saveComment(no, commentRequestDTO);

        return "redirect:/post/" + no;
    }
}

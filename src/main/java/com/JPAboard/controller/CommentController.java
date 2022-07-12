package com.JPAboard.controller;

import com.JPAboard.domain.entity.CommentEntity;
import com.JPAboard.dto.CommentDTO;
import com.JPAboard.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
public class CommentController {
    private CommentService commentService;

    @GetMapping("/post/{no}/comments")
    public String write(@PathVariable("no") Long no) {
        return "redirect:/post/"+no;
    }

    /*@PostMapping("/post/{no}/comments")
    public ResponseEntity write(@PathVariable("no") Long no, CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.saveComment(no, commentDTO));
    }*/
    @PostMapping("/post/{no}/comments")
    public String write(@PathVariable("no") Long no, @ModelAttribute("board") CommentDTO commentDTO) {
        commentService.saveComment(no, commentDTO);

        return "redirect:/post/" + no;
    }
}

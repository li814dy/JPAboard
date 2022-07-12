package com.JPAboard.controller;

import com.JPAboard.dto.CommentRequestDTO;
import com.JPAboard.dto.CommentResponseDTO;
import com.JPAboard.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/post/{no}/comments/{cno}/edit")
    public String edit(@PathVariable("no") Long no, @PathVariable("cno") Long cno, Model model) {
        CommentResponseDTO commentResponseDTO = commentService.getComment(cno);
        model.addAttribute("comment", commentResponseDTO);

        return "redirect:/post/" + no;
    }

    @PutMapping("/post/{no}/comments/{cno}/edit")
    public String update(@PathVariable("no") Long no, @PathVariable("cno") Long cno, CommentRequestDTO commentRequestDTO) {
        commentService.saveComment(cno, commentRequestDTO);

        return "redirect:/post/" + no;
    }

    @DeleteMapping("/post/{no}/comments/{cno}")
    public String delete(@PathVariable("no") Long no, @PathVariable("cno") Long cno) {
        commentService.deleteComment(cno);

        return "redirect:/post/" + no;
    }
}

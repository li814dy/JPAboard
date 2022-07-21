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

    @GetMapping("/post/{no}/comment")
    public String write(@PathVariable("no") Long no) {
        return "/post/"+no+"/comments";
    }

    @PostMapping("/post/{no}/comment")
    public String write(@PathVariable("no") Long no, CommentRequestDTO commentRequestDTO) {
        commentService.saveComment(no, commentRequestDTO);

        return "redirect:/post/" + no;
    }

    @GetMapping("/post/{no}/comment/{cno}/edit")
    public String edit() {
        return "comment/list";
    }

    @PutMapping("/post/{no}/comment/{cno}/edit")
    public String update(@PathVariable("no") Long no, @PathVariable("cno") Long cno, CommentRequestDTO commentRequestDTO, Model model, String password) {
        CommentResponseDTO commentResponseDTO = commentService.getComment(cno);
        if (commentResponseDTO != null) {
            if (commentResponseDTO.getPassword().equals(password)) {
                model.addAttribute("comment", commentResponseDTO);
                commentService.saveComment(cno, commentRequestDTO);
            } else {
                model.addAttribute("");
            }
        }
        return "redirect:/post/" + no;
    }

    @DeleteMapping("/post/{no}/comment/{cno}")
    public String delete(@PathVariable("no") Long no, @PathVariable("cno") Long cno) {
        commentService.deleteComment(cno);

        return "redirect:/post/" + no;
    }
}

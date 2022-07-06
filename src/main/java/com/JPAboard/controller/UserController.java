package com.JPAboard.controller;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.dto.UserDto;
import com.JPAboard.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class UserController {
    private UserService userService;

    // 회원가입 페이지 연결
    @GetMapping("/user/join")
    public String userJoin() {
        return "user/join";
    }

    // 회원가입 실행
    @PostMapping("/user/join")
    public String userJoin(UserDto userDto) {
        userService.userJoin(userDto);

        return "user/login";
    }

    // 로그인 페이지 연결
    @GetMapping("/user/login")
    public String userLogin() {
        return "user/login";
    }

    // 로그인 실행
    @PostMapping("/user/login")
    public String userLogin(UserEntity userEntity, Model model, HttpSession session) {
        UserEntity userE = userService.userLogin(userEntity.getUserId(), userEntity.getUserPw());
        if(userE != null) {
            session.setAttribute("user", userE);
            session.setAttribute("userName", userE.getUserName());
            model.addAttribute("userData", userE);

            return "redirect:/";
        } else {
            model.addAttribute("loginFail", "아이디 혹은 비밀번호가 일치하지 않습니다.");

            return "redirect:/";
        }
    }

    // 로그아웃 연결
    @GetMapping("/user/logout")
    public String userLogout() {
        return "user/logout";
    }

    // 로그아웃 실행
    @PostMapping("/user/logout")
    public String userLogout(Model model, HttpSession session) {
        model.addAttribute("userData", null);
        session.removeAttribute("user");
        session.removeAttribute("userName");
        session.invalidate();

        return "redirect:/";
    }
}

package com.JPAboard.controller;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.dto.UserDto;
import com.JPAboard.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class UserController {
    private UserService userService;

    // 로그인 페이지 연결
    @GetMapping("/user/login")
    public String userLogin() {
        return "user/login.html";
    }

    // 로그인 실행
    @PostMapping("/user/login")
    public String userLogin(UserEntity userEntity, HttpSession session) {
        userEntity = userService.userLogin(userEntity);
        if(userEntity != null) {
            session.setAttribute("user", userEntity);
        }
        return "redirect:/";
    }

    // 로그아웃 연결
    @GetMapping("/user/logout")
    public void userLogout() {}

    // 로그아웃 실행
    @PostMapping("/user/logout")
    public String userLogout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }

    // 회원가입 페이지 연결
    @GetMapping("/user/join")
    public String userJoin() {
        return "user/join.html";
    }

    // 회원가입 실행
    @PostMapping("/user/join")
    public String userJoin(UserDto userDto) {
        userService.userJoin(userDto);

        return "user/login.html";
    }
}

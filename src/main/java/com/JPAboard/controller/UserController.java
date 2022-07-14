package com.JPAboard.controller;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.dto.UserRequestDTO;
import com.JPAboard.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String userJoin(UserEntity userEntity, Model model, UserRequestDTO userRequestDTO) {
        UserEntity userE = userService.userJoinCheck(userEntity.getUserId());
        if(userE != null) {
            model.addAttribute("joinFail", "같은 아이디를 가진 회원이 이미 존재합니다!");

            return "user/join";
        } else {
            userService.userJoin(userRequestDTO);

            return "user/login";
        }
    }

/*    // 회원가입 실행
    @PostMapping("/user/join")
    public String userJoin(UserRequestDTO userRequestDTO, Model model, String userId) {
        UserResponseDTO userResponseDTO = userService.userJoinCheck(userId);
        if(userResponseDTO != null) {
            model.addAttribute("joinFail", "같은 아이디를 가진 회원이 이미 존재합니다!");

            return "user/join";
        } else {
            userService.userJoin(userRequestDTO);

            return "user/login";
        }
    }*/

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

            return "user/login";
        }
    }

/*    // 로그인 실행
    @PostMapping("/user/login")
    public String userLogin(Model model, HttpSession session, String userId, String userPw) {
        UserResponseDTO userResponseDTO = userService.userLogin(userId, userPw);
        if(userResponseDTO != null) {
            session.setAttribute("user", userResponseDTO);
            session.setAttribute("userName", userResponseDTO.getUserName());
            model.addAttribute("userData", userResponseDTO);

            return "redirect:/";
        } else {
            model.addAttribute("loginFail", "아이디 혹은 비밀번호가 일치하지 않습니다.");

            return "user/login";
        }
    }*/

    // 비밀번호 찾기 페이지 연결
    @GetMapping("/user/findpw")
    public String findPW() {
        return "user/findpw";
    }

    // 비밀번호 찾기 실행
    @PostMapping("/user/findpw")
    public String findPW(UserEntity userEntity, Model model) {
        UserEntity getuserPw = userService.findPW(userEntity.getUserName(), userEntity.getUserId());
        if(getuserPw != null) {
            model.addAttribute("userPw", getuserPw.getUserPw());

            return "user/findpw";
        } else {
            model.addAttribute("findFail", "일치하는 유저 정보가 없습니다.");

            return "user/findpw";
        }
    }

/*    // 비밀번호 찾기 실행
    @PostMapping("/user/findpw")
    public String findPW(Model model, String userName, String userId) {
        UserResponseDTO userResponseDTO = userService.findPW(userName, userId);
        if(userResponseDTO != null) {
            model.addAttribute("userPw", userResponseDTO.getUserPw());

            return "user/findpw";
        } else {
            model.addAttribute("findFail", "일치하는 유저 정보가 없습니다.");

            return "user/findpw";
        }
    }*/

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

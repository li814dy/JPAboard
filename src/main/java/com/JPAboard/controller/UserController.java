package com.JPAboard.controller;

import com.JPAboard.dto.*;
import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.service.UserService;
import com.JPAboard.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

@AllArgsConstructor
@Controller
public class UserController {
    private UserService userService;
    private FileService fileService;

    // 회원가입 페이지 연결
    @GetMapping("/user/join")
    public String join() {
        return "user/join";
    }

    // 회원가입 실행
    @PostMapping("/user/join")
    public String join(@RequestParam(value = "file", required = false) MultipartFile multipartFile, UserResponseDTO userEntity, UserRequestDTO userRequestDTO, Model model) {
        UserEntity userE = userService.checkUser(userEntity.getUserId());
        if(userE != null) {
            model.addAttribute("joinFail", "같은 아이디를 가진 회원이 이미 존재합니다!");

            return "user/join";
        } else {
            if(!multipartFile.isEmpty()) {
                try {
                    String fileName = multipartFile.getOriginalFilename();
                    String fileType = StringUtils.getFilenameExtension(fileName);
                    String fileSize = Long.toString(multipartFile.getSize());

                    String makePath = System.getProperty("user.dir") + "\\files\\users";

                    if (!ObjectUtils.isEmpty(fileType)) {
                        if (!new File(makePath).exists()) {
                            try {
                                new File(makePath).mkdir();
                            } catch (Exception e) {
                                e.getStackTrace();
                            }
                        }
                    }

                    String filePath = makePath + "\\" + fileName;
                    multipartFile.transferTo(new File(filePath));

                    FileRequestDTO fileRequestDTO = new FileRequestDTO();
                    fileRequestDTO.setFileName(fileName);
                    fileRequestDTO.setFilePath(filePath);
                    fileRequestDTO.setFileType(fileType);
                    fileRequestDTO.setFileSize(fileSize);

                    Long fileId = fileService.saveFile(fileRequestDTO);
                    userRequestDTO.setFileId(fileId);
                    userService.joinUser(userRequestDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                userService.joinUser(userRequestDTO);
            }

            return "redirect:/user/login";
        }
    }

    // 로그인 페이지 연결
    @GetMapping("/user/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/user/login")
    public String login(Model model, HttpServletRequest request, String userId, String userPw) {
        UserResponseDTO userResponseDTO = userService.loginUserDTO(userId, userPw);

        if(userResponseDTO != null) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(600);            // 10분
            session.setAttribute("user", userResponseDTO);

            if (userResponseDTO.getFileId() != null) {
                FileResponseDTO fileResponseDTO = fileService.getFile(userResponseDTO.getFileId());
                session.setAttribute("userFile", fileResponseDTO);
            }

            return "redirect:/";
        } else {
            model.addAttribute("loginFail", "아이디 혹은 비밀번호가 일치하지 않습니다.");

            return "user/login";
        }
    }

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

    // 로그아웃 연결
    @GetMapping("/user/logout")
    public String logout() {
        return "user/logout";
    }

    // 로그아웃 실행
    @PostMapping("/user/logout")
    public String logout(Model model, HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/user/{userId}")
    public String detailUser(HttpServletRequest request, HttpSession session) {
        if (request.getSession() != null | session.getAttribute("user") != null) {
            session = request.getSession();
            session.getAttribute("user");
        }
        return "user/detail";
    }

    @GetMapping("/user/{userId}/edit")
    public String editUser(HttpServletRequest request, HttpSession session) {
        if (request.getSession() != null | session.getAttribute("user") != null) {
            session = request.getSession();
            session.getAttribute("user");
        }
        return "user/update";
    }

    @PutMapping("/user/{userId}/edit")
    public String updateUser(UserRequestDTO userRequestDTO, HttpServletRequest request, HttpSession session) {
        userService.joinUser(userRequestDTO);

        UserEntity userE = userService.loginUser(userRequestDTO.getUserId(), userRequestDTO.getUserPw());
        UserResponseDTO userResponseDTO = userService.getUser(userE.getUserNum());

        session.setAttribute("user", userResponseDTO);

        if (request.getSession() != null | session.getAttribute("user") != null) {
            session = request.getSession();
            session.getAttribute("user");
        }

        return "redirect:/user/{userId}";
    }

    @DeleteMapping("/user/{userId}")
    public String deleteUser(@PathVariable("userId") String userId, UserResponseDTO userResponseDTO, HttpSession session) {
        userResponseDTO = userService.checkUserId(userId);
        userService.deleteUser(userResponseDTO.getUserNum());

        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
            session.invalidate();
        }

        return "redirect:/";
    }
}

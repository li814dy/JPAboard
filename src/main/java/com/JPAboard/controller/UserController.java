package com.JPAboard.controller;

import com.JPAboard.domain.entity.UserEntity;
import com.JPAboard.dto.FileRequestDTO;
import com.JPAboard.dto.FileResponseDTO;
import com.JPAboard.dto.UserRequestDTO;
import com.JPAboard.dto.UserResponseDTO;
import com.JPAboard.service.FileService;
import com.JPAboard.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
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
    public String userJoin() {
        return "user/join";
    }

    // 회원가입 실행
/*    @PostMapping("/user/join")
    public String userJoin(UserEntity userEntity, Model model, UserRequestDTO userRequestDTO) {
        UserEntity userE = userService.userJoinCheck(userEntity.getUserId());
        if(userE != null) {
            model.addAttribute("joinFail", "같은 아이디를 가진 회원이 이미 존재합니다!");

            return "user/join";
        } else {
            userService.userJoin(userRequestDTO);

            return "user/login";
        }
    }*/

    @PostMapping("/user/join")
    public String userJoin(@RequestParam(value = "file", required = false) MultipartFile multipartFile, UserEntity userEntity, UserRequestDTO userRequestDTO, Model model) {
        UserEntity userE = userService.userJoinCheck(userEntity.getUserId());
        if(userE != null) {
            model.addAttribute("joinFail", "같은 아이디를 가진 회원이 이미 존재합니다!");

            return "user/join";
        } else {
            if(!multipartFile.isEmpty()) {
                try {
                    String fileName = multipartFile.getOriginalFilename();
                    String fileType = StringUtils.getFilenameExtension(fileName);
                    String fileSize = Long.toString(multipartFile.getSize());

                    String makePath = System.getProperty("user.dir") + "\\files";

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
                    userService.userJoin(userRequestDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                userService.userJoin(userRequestDTO);
            }

            return "redirect:user/login";
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
    public String userLogin(UserEntity userEntity, Model model, HttpServletRequest request) {
        UserEntity userE = userService.userLogin(userEntity.getUserId(), userEntity.getUserPw());
        UserResponseDTO userResponseDTO = userService.getUser(userE.getUserNum());

        if(userE != null) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(600);            // 10분
            session.setAttribute("user", userResponseDTO);
            session.setAttribute("userFile", userResponseDTO.getFileId());

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
        session.removeAttribute("user");
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/user/{userId}")
    public String userDetail(HttpServletRequest request, HttpSession session) {
        if (request.getSession() != null | session.getAttribute("user") != null) {
            session = request.getSession();
            session.getAttribute("user");
        }
        return "user/detail";
    }

    @GetMapping("/user/{userId}/edit")
    public String userEdit(HttpServletRequest request, HttpSession session) {
        if (request.getSession() != null | session.getAttribute("user") != null) {
            session = request.getSession();
            session.getAttribute("user");
        }
        return "user/update";
    }

    @PutMapping("/user/{userId}/edit")
    public String userUpdate(UserRequestDTO userRequestDTO, HttpServletRequest request, HttpSession session) {
        userService.userJoin(userRequestDTO);

        UserEntity userE = userService.userLogin(userRequestDTO.getUserId(), userRequestDTO.getUserPw());
        UserResponseDTO userResponseDTO = userService.getUser(userE.getUserNum());

        session.setAttribute("user", userResponseDTO);

        if (request.getSession() != null | session.getAttribute("user") != null) {
            session = request.getSession();
            session.getAttribute("user");
        }

        return "redirect:/user/{userId}";
    }

    @DeleteMapping("/user/{userId}")
    public String delete(@PathVariable("userId") String userId, UserResponseDTO userResponseDTO, HttpSession session) {
        userResponseDTO = userService.userCheck(userId);
        userService.deleteUser(userResponseDTO.getUserNum());

        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
            session.invalidate();
        }

        return "redirect:/";
    }
}

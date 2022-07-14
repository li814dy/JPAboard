package com.JPAboard.controller;

import com.JPAboard.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class FileController {
    private FileService fileService;
}

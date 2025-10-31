package com.design.controller.file;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/file")
@Tag(name = "檔案")
@RestController
@RequiredArgsConstructor
@Validated
public class FileController {



}

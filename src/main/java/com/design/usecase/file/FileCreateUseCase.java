package com.design.usecase.file;

import com.design.controller.file.request.FileCreateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileCreateUseCase {

    void create(FileCreateRequest request, List<MultipartFile> files);

}

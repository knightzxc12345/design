package com.design.usecase.file;

import com.design.controller.file.request.FileEditRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileEditUseCase {

    void edit(FileEditRequest request, List<MultipartFile> files);

}

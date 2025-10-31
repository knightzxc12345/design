package com.design.usecase.file.impl;

import com.design.controller.file.request.FileEditRequest;
import com.design.service.FileService;
import com.design.service.QuotationService;
import com.design.usecase.file.FileEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileEditUseCaseImpl implements FileEditUseCase {

    private final FileService fileService;

    private final QuotationService quotationService;

    @Override
    public void edit(FileEditRequest request, List<MultipartFile> files) {

    }

}

package com.design.usecase.file.impl;

import com.design.controller.file.request.FileCreateRequest;
import com.design.service.FileService;
import com.design.service.QuotationService;
import com.design.usecase.file.FileCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileCreateUseCaseImpl implements FileCreateUseCase {

    private final FileService fileService;

    private final QuotationService quotationService;

    @Override
    public void create(FileCreateRequest request, List<MultipartFile> files) {

    }

}

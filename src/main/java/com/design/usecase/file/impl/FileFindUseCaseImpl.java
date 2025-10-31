package com.design.usecase.file.impl;

import com.design.controller.file.request.FileFindRequest;
import com.design.controller.file.request.FilePageRequest;
import com.design.controller.file.response.FileFindAllResponse;
import com.design.controller.file.response.FilePageResponse;
import com.design.entity.FileEntity;
import com.design.service.FileService;
import com.design.usecase.file.FileFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileFindUseCaseImpl implements FileFindUseCase {

    private final FileService fileService;

    @Override
    public List<FileFindAllResponse> findAll(FileFindRequest request) {
        return null;
    }

    @Override
    public FilePageResponse findByPage(FilePageRequest request) {
        return null;
    }

    private List<FileFindAllResponse> formatList(List<FileEntity> fileEntities) {
        return null;
    }

}

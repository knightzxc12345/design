package com.design.usecase.file.impl;

import com.design.base.api.FileCode;
import com.design.base.common.Common;
import com.design.controller.file.request.FileCreateRequest;
import com.design.entity.FileEntity;
import com.design.entity.QuotationEntity;
import com.design.handler.BusinessException;
import com.design.service.FileService;
import com.design.service.QuotationService;
import com.design.usecase.file.FileCreateUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class FileCreateUseCaseImpl implements FileCreateUseCase {

    private final FileService fileService;

    private final QuotationService quotationService;

    @Override
    public void create(FileCreateRequest request, List<MultipartFile> files) {
        List<FileEntity> fileEntities = convert(request, files);
        fileService.createAll(fileEntities);
    }

    private List<FileEntity> convert(FileCreateRequest request, List<MultipartFile> files){
        List<String> tags = request.tags();
        List<String> remarks = request.remarks();
        if(tags.size() != remarks.size() || tags.size() != files.size() || remarks.size() != files.size()){
            throw new BusinessException(FileCode.FILE_CONTENT_ERROR);
        }
        QuotationEntity quotation = quotationService.findByUuid(request.quotationUuid());
        return IntStream.range(0, files.size())
                .mapToObj(i -> {
                    String imageUrl = ImageUtil.uploadImage(Common.IMAGE_PATH_PRODUCT, files.get(i));
                    FileEntity fileEntity = new FileEntity();
                    fileEntity.setQuotation(quotation);
                    fileEntity.setTag(tags.get(i));
                    fileEntity.setRemark(remarks.get(i));
                    fileEntity.setImageUrl(imageUrl);
                    return fileEntity;
                })
                .collect(Collectors.toList());
    }

}

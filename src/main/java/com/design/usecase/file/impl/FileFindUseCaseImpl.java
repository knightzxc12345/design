package com.design.usecase.file.impl;

import com.design.controller.common.response.PageResponse;
import com.design.controller.file.request.FileFindRequest;
import com.design.controller.file.request.FilePageRequest;
import com.design.controller.file.response.FileFindAllResponse;
import com.design.controller.file.response.FilePageResponse;
import com.design.entity.FileEntity;
import com.design.service.FileService;
import com.design.usecase.file.FileFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileFindUseCaseImpl implements FileFindUseCase {

    private final FileService fileService;

    @Override
    public List<FileFindAllResponse> findAll(FileFindRequest request) {
        List<FileEntity> fileEntities = fileService.findAll(request.keyword());
        return formatList(fileEntities);
    }

    @Override
    public FilePageResponse findByPage(FilePageRequest request) {
        Page<FileEntity> fileEntityPage = fileService.findByPage(
                request.keyword(),
                PageRequest.of(request.page(), request.size())
        );
        return formatPage(fileEntityPage);
    }

    private List<FileFindAllResponse> formatList(List<FileEntity> fileEntities) {
        if (fileEntities == null || fileEntities.isEmpty()) {
            return List.of();
        }
        Map<String, List<FileEntity>> grouped = fileEntities.stream()
                .collect(Collectors.groupingBy(f -> f.getQuotation().getUuid()));
        return grouped.entrySet().stream()
                .map(entry -> {
                    List<FileEntity> files = entry.getValue();
                    var first = files.get(0);
                    List<FileFindAllResponse.File> fileList = files.stream()
                            .map(f -> new FileFindAllResponse.File(
                                    f.getTag(),
                                    f.getRemark(),
                                    f.getImageUrl()
                            ))
                            .toList();
                    return new FileFindAllResponse(
                            first.getQuotation().getUuid(),
                            first.getQuotation().getQuotationNo(),
                            first.getQuotation().getCustomer().getName(),
                            fileList
                    );
                })
                .toList();
    }

    private FilePageResponse formatPage(Page<FileEntity> fileEntityPage){
        List<FileFindAllResponse> responses = formatList(fileEntityPage.getContent());
        return new FilePageResponse(
                new PageResponse(
                        fileEntityPage.getNumber(),
                        fileEntityPage.getSize(),
                        fileEntityPage.getTotalElements(),
                        fileEntityPage.getTotalPages()
                ),
                responses
        );
    }

}

package com.design.service.impl;

import com.design.entity.FileEntity;
import com.design.repository.FileRepository;
import com.design.service.FileService;
import com.design.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public void createAll(List<FileEntity> fileEntities) {
        if(null == fileEntities || fileEntities.isEmpty()){
            return;
        }
        for(FileEntity fileEntity : fileEntities){
            fileEntity.setUuid(UUID.randomUUID().toString());
            fileEntity.setCreateTime(Instant.now());
            fileEntity.setCreateUser(UserUtil.getUserUuid());
        }
        fileRepository.saveAll(fileEntities);
    }

    @Override
    public void deleteAll(List<FileEntity> fileEntities) {
        if(null == fileEntities || fileEntities.isEmpty()){
            return;
        }
        fileRepository.deleteAll(fileEntities);
    }

    @Override
    public List<FileEntity> findAllByUuid(String quotationUuid) {
        return fileRepository.findByQuotation_UuidOrderByQuotation_PkAsc(quotationUuid);
    }

    @Override
    public List<FileEntity> findAll(String keyword) {
        return fileRepository.findAll(keyword);
    }

    public Page<FileEntity> findByPage(String keyword, Pageable pageable) {
        return fileRepository.findByPage(
                keyword,
                pageable
        );
    }

}

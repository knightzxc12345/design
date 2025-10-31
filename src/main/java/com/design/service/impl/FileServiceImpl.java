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
    public void createFolder(FileEntity fileEntity) {
        fileEntity.setUuid(UUID.randomUUID().toString());
        fileEntity.setCreateTime(Instant.now());
        fileEntity.setCreateUser(UserUtil.getUserUuid());
        fileEntity.setIsFolder(true);
    }

    @Override
    public void createFile(FileEntity fileEntity) {
        fileEntity.setUuid(UUID.randomUUID().toString());
        fileEntity.setCreateTime(Instant.now());
        fileEntity.setCreateUser(UserUtil.getUserUuid());
        fileEntity.setIsFolder(false);
    }

    @Override
    public List<FileEntity> findAllByUuid(String uuid) {
        return fileRepository.findByUuidOrderByUuidAsc(uuid);
    }

    @Override
    public Page<FileEntity> findByPage(String keyword, Pageable pageable) {
        return fileRepository.findByPage(
                keyword,
                pageable
        );
    }

}

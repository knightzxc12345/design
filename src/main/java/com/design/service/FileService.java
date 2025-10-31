package com.design.service;

import com.design.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FileService {

    void createFolder(FileEntity fileEntity);

    void createFile(FileEntity fileEntity);

    List<FileEntity> findAllByUuid(String uuid);

    Page<FileEntity> findByPage(String keyword, Pageable pageable);

}

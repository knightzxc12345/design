package com.design.service;

import com.design.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FileService {

    void createAll(List<FileEntity> fileEntities);

    void deleteAll(List<FileEntity> fileEntities);

    List<FileEntity> findAllByUuid(String quotationUuid);

    List<FileEntity> findAll(String keyword);

    Page<FileEntity> findByPage(String keyword, Pageable pageable);

}

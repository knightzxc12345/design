package com.erp.service;

import com.erp.entity.CategoryEntity;
import com.erp.entity.enums.CategoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryEntity create(CategoryEntity categoryEntity);

    CategoryEntity edit(CategoryEntity categoryEntity);

    CategoryEntity delete(UUID uuid);

    CategoryEntity findByUuid(UUID uuid);

    List<CategoryEntity> findAll(String keyword, CategoryStatus categoryStatus);

    Page<CategoryEntity> findByPage(Pageable pageable, String keyword, CategoryStatus categoryStatus);

}

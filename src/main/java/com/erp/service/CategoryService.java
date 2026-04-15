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

    List<CategoryEntity> deleteAllByBrandUuid(UUID brandUuid);

    CategoryEntity findByUuid(UUID uuid);

    List<CategoryEntity> findAll(
            UUID brandUuid,
            String keyword,
            CategoryStatus categoryStatus
    );

    List<CategoryEntity> findAllByBrandUuid(UUID brandUuid);

    Page<CategoryEntity> findByPage(
            Pageable pageable,
            UUID brandUuid,
            String keyword,
            CategoryStatus categoryStatus
    );

}

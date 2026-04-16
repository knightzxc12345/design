package com.erp.service.impl;

import com.erp.base.response.enums.CategoryCode;
import com.erp.entity.CategoryEntity;
import com.erp.entity.enums.CategoryStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.CategoryRepository;
import com.erp.service.CategoryService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryEntity create(CategoryEntity categoryEntity) {
        // 檢查名稱
        categoryRepository.findByIsDeletedFalseAndName(
                categoryEntity.getName()
        ).ifPresent(c -> {
            throw new BusinessException(CategoryCode.DUPLICATE_NAME);
        });
        // 檢查代碼
        categoryRepository.findByIsDeletedFalseAndCode(
                categoryEntity.getCode()
        ).ifPresent(c -> {
            throw new BusinessException(CategoryCode.DUPLICATE_CODE);
        });
        categoryEntity.setStatus(CategoryStatus.ENABLE);
        categoryEntity.setIsDeleted(false);
        categoryEntity.setDeletedTime(Instant.now());
        categoryEntity.setDeletedUser(UserUtil.getUserUuid());
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoryEntity edit(CategoryEntity categoryEntity) {
        // 檢查名稱
        categoryRepository.findByIsDeletedFalseAndName(
                categoryEntity.getName()
        ).ifPresent(c -> {
            if(!c.getUuid().equals(categoryEntity.getUuid())){
                throw new BusinessException(CategoryCode.DUPLICATE_NAME);
            }
        });
        // 檢查代碼
        categoryRepository.findByIsDeletedFalseAndCode(
                categoryEntity.getCode()
        ).ifPresent(c -> {
            if(!c.getUuid().equals(categoryEntity.getUuid())){
                throw new BusinessException(CategoryCode.DUPLICATE_CODE);
            }
        });
        categoryEntity.setModifiedTime(Instant.now());
        categoryEntity.setModifiedUser(UserUtil.getUserUuid());
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoryEntity delete(UUID uuid) {
        CategoryEntity categoryEntity = findByUuid(uuid);
        categoryEntity.setIsDeleted(true);
        categoryEntity.setDeletedTime(Instant.now());
        categoryEntity.setDeletedUser(UserUtil.getUserUuid());
        return categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoryEntity findByUuid(UUID uuid) {
        return categoryRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(CategoryCode.NOT_EXISTS));
    }

    @Override
    public List<CategoryEntity> findAll(
            String keyword,
            CategoryStatus categoryStatus) {
        return categoryRepository.findAll(
                keyword,
                categoryStatus
        );
    }

    @Override
    public Page<CategoryEntity> findPageByBrandUuid(
            Pageable pageable,
            String keyword,
            CategoryStatus categoryStatus) {
        return categoryRepository.findPage(
                pageable,
                keyword,
                categoryStatus
        );
    }

}

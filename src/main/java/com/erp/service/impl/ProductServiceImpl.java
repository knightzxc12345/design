package com.erp.service.impl;

import com.erp.base.response.enums.ProductCode;
import com.erp.entity.ProductEntity;
import com.erp.entity.enums.ProductStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.ProductRepository;
import com.erp.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductEntity create(ProductEntity productEntity) {
        // 檢查名稱
        productRepository.findByIsDeletedFalseAndCategoryUuidAndName(
                productEntity.getCategoryUuid(),
                productEntity.getName()
        ).ifPresent(c -> {
            throw new BusinessException(ProductCode.DUPLICATE_NAME);
        });
        // 檢查代碼
        productRepository.findByIsDeletedFalseAndCategoryUuidAndCode(
                productEntity.getCategoryUuid(),
                productEntity.getCode()
        ).ifPresent(c -> {
            throw new BusinessException(ProductCode.DUPLICATE_CODE);
        });
        productEntity.setStatus(ProductStatus.ENABLE);
        productEntity.setIsDeleted(false);
        productEntity.setCreateTime(Instant.now());
        productEntity.setCreateUser(UserUtil.getUserUuid());
        return productRepository.save(productEntity);
    }

    @Override
    public ProductEntity edit(ProductEntity productEntity) {
        // 檢查名稱
        productRepository.findByIsDeletedFalseAndCategoryUuidAndName(
                productEntity.getCategoryUuid(),
                productEntity.getName()
        ).ifPresent(p -> {
            if(!p.getUuid().equals(productEntity.getUuid())){
                throw new BusinessException(ProductCode.DUPLICATE_NAME);
            }
        });
        // 檢查代碼
        productRepository.findByIsDeletedFalseAndCategoryUuidAndCode(
                productEntity.getCategoryUuid(),
                productEntity.getCode()
        ).ifPresent(p -> {
            if(!p.getUuid().equals(productEntity.getUuid())){
                throw new BusinessException(ProductCode.DUPLICATE_CODE);
            }
        });
        productEntity.setModifiedTime(Instant.now());
        productEntity.setModifiedUser(UserUtil.getUserUuid());
        return productRepository.save(productEntity);
    }

    @Override
    public ProductEntity delete(UUID uuid) {
        ProductEntity productEntity = findByUuid(uuid);
        productEntity.setIsDeleted(true);
        productEntity.setDeletedTime(Instant.now());
        productEntity.setDeletedUser(UserUtil.getUserUuid());
        return productRepository.save(productEntity);
    }

    @Override
    public ProductEntity findByUuid(UUID uuid) {
        return productRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(ProductCode.NOT_EXISTS));
    }

    @Override
    public List<ProductEntity> findAllByBrandUuidAndCategoryUuid(
            UUID brandUuid,
            UUID categoryUuid,
            String keyword,
            ProductStatus productStatus) {
        return productRepository.findAll(
                brandUuid,
                categoryUuid,
                keyword,
                productStatus
        );
    }

    @Override
    public Page<ProductEntity> findPageByBrandUuidAndCategoryUuid(
            Pageable pageable,
            UUID brandUuid,
            UUID categoryUuid,
            String keyword,
            ProductStatus productStatus) {
        return productRepository.findPage(
                pageable,
                brandUuid,
                categoryUuid,
                keyword,
                productStatus
        );
    }

}

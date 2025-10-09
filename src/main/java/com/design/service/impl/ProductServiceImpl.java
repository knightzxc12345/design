package com.design.service.impl;

import com.design.base.api.ItemCode;
import com.design.base.api.ProductCode;
import com.design.entity.ProductEntity;
import com.design.entity.enums.ProductStatus;
import com.design.handler.BusinessException;
import com.design.repository.ProductRepository;
import com.design.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void create(ProductEntity productEntity) {
        if (productRepository.findByCodeAndIsDeletedFalse(productEntity.getCode()).isPresent()) {
            throw new BusinessException(ItemCode.DUPLICATE_CODE);
        }
        productEntity.setStatus(ProductStatus.ACTIVE);
        productEntity.setUuid(UUID.randomUUID().toString());
        productEntity.setCreateTime(Instant.now());
        productEntity.setCreateUser(UserUtil.getUserUuid());
        productEntity.setIsDeleted(false);
        productRepository.save(productEntity);
    }

    @Override
    public void edit(ProductEntity productEntity) {
        if (productRepository.findByCodeAndUuidNotAndIsDeletedFalse(productEntity.getCode(), productEntity.getUuid()).isPresent()) {
            throw new BusinessException(ItemCode.DUPLICATE_CODE);
        }
        productEntity.setModifiedTime(Instant.now());
        productEntity.setModifiedUser(UserUtil.getUserUuid());
        productRepository.save(productEntity);
    }

    @Override
    public void delete(ProductEntity productEntity) {
        productEntity.setModifiedTime(Instant.now());
        productEntity.setModifiedUser(UserUtil.getUserUuid());
        productEntity.setIsDeleted(true);
        productEntity.setDeletedTime(Instant.now());
        productEntity.setDeletedUser(UserUtil.getUserUuid());
        productRepository.save(productEntity);
    }

    @Override
    public ProductEntity findByUuid(UUID uuid) {
        return productRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new BusinessException(ProductCode.NOT_EXISTS));
    }

    @Override
    public List<ProductEntity> findAll(String keyword) {
        return productRepository.findAll(
                keyword
        );
    }

    @Override
    public Page<ProductEntity> findByPage(String keyword, Pageable pageable) {
        return productRepository.findByPage(
                keyword,
                pageable
        );
    }

}

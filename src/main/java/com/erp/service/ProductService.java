package com.erp.service;

import com.erp.entity.ProductEntity;
import com.erp.entity.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductEntity create(ProductEntity productEntity);

    ProductEntity edit(ProductEntity productEntity);

    ProductEntity delete(UUID uuid);

    ProductEntity findByUuid(UUID uuid);

    List<ProductEntity> findAllByBrandUuidAndCategoryUuid(
            UUID brandUuid,
            UUID categoryUuid,
            String keyword,
            ProductStatus productStatus
    );

    Page<ProductEntity> findPageByBrandUuidAndCategoryUuid(
            Pageable pageable,
            UUID brandUuid,
            UUID categoryUuid,
            String keyword,
            ProductStatus productStatus
    );

}

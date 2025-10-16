package com.design.usecase.product.impl;

import com.design.entity.ProductEntity;
import com.design.entity.ProductItemEntity;
import com.design.service.ProductItemService;
import com.design.service.ProductService;
import com.design.usecase.product.ProductDeleteUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDeleteUseCaseImpl implements ProductDeleteUseCase {

    private final ProductService productService;

    private final ProductItemService productItemService;

    @Transactional
    @Override
    public void delete(String uuid) {
        ProductEntity productEntity = productService.findByUuid(uuid);
        List<ProductItemEntity> productItemEntities = productItemService.findAll(productEntity.getUuid());
        ImageUtil.deleteImage(productEntity.getImageUrl());
        productService.delete(productEntity);
        productItemService.deleteAll(productItemEntities);
    }

}

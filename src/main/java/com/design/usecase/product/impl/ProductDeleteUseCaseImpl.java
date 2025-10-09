package com.design.usecase.product.impl;

import com.design.entity.ProductEntity;
import com.design.service.ProductService;
import com.design.usecase.product.ProductDeleteUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDeleteUseCaseImpl implements ProductDeleteUseCase {

    private final ProductService productService;

    @Override
    public void delete(String uuid) {
        ProductEntity productEntity = productService.findByUuid(uuid);
        ImageUtil.deleteImage(productEntity.getImageUrl());
        productService.delete(productEntity);
    }

}

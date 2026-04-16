package com.erp.usecase.product.impl;

import com.erp.controller.product.request.ProductCreateRequest;
import com.erp.entity.ProductEntity;
import com.erp.service.ProductService;
import com.erp.usecase.product.ProductCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCreateUseCaseImpl implements ProductCreateUseCase {

    private final ProductService productService;

    @Override
    public void create(ProductCreateRequest request) {
        ProductEntity productEntity = ProductEntity.builder()
                .brandUuid(request.brandUuid())
                .categoryUuid(request.categoryUuid())
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .build();
        productService.create(productEntity);
    }

}

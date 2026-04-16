package com.erp.usecase.product.impl;

import com.erp.controller.product.request.ProductEditRequest;
import com.erp.entity.ProductEntity;
import com.erp.service.ProductService;
import com.erp.usecase.product.ProductEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductEditUseCaseImpl implements ProductEditUseCase {

    private final ProductService productService;

    @Override
    public void edit(UUID uuid, ProductEditRequest request) {
        ProductEntity productEntity = productService.findByUuid(uuid);
        productEntity = init(productEntity, request);
        productService.edit(productEntity);
    }

    private ProductEntity init(ProductEntity productEntity, ProductEditRequest request){
        productEntity.setBrandUuid(request.brandUuid());
        productEntity.setCategoryUuid(request.categoryUuid());
        productEntity.setName(request.name());
        productEntity.setCode(request.code());
        productEntity.setDescription(request.description());
        return productEntity;
    }

}

package com.erp.usecase.product.impl;

import com.erp.service.ProductService;
import com.erp.usecase.product.ProductDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductDeleteUseCaseImpl implements ProductDeleteUseCase {

    private final ProductService productService;

    @Override
    public void delete(UUID uuid) {
        productService.delete(uuid);
    }

}

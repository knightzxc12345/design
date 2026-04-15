package com.erp.usecase.brand.impl;

import com.erp.controller.brand.request.BrandCreateRequest;
import com.erp.entity.BrandEntity;
import com.erp.service.BrandService;
import com.erp.usecase.brand.BrandCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandCreateUseCaseImpl implements BrandCreateUseCase {

    private final BrandService brandService;

    @Override
    public void create(BrandCreateRequest request) {
        BrandEntity brandEntity = BrandEntity.builder()
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .build();
        brandService.create(brandEntity);
    }

}

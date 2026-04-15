package com.erp.usecase.brand.impl;

import com.erp.controller.brand.request.BrandEditRequest;
import com.erp.entity.BrandEntity;
import com.erp.service.BrandService;
import com.erp.usecase.brand.BrandEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandEditUseCaseImpl implements BrandEditUseCase {

    private final BrandService brandService;

    @Override
    public void edit(UUID uuid, BrandEditRequest request) {
        BrandEntity brandEntity = brandService.findByUuid(uuid);
        brandEntity = init(brandEntity, request);
        brandService.edit(brandEntity);
    }

    private BrandEntity init(BrandEntity brandEntity, BrandEditRequest request){
        brandEntity.setName(request.name());
        brandEntity.setCode(request.code());
        brandEntity.setDescription(request.description());
        brandEntity.setStatus(request.status());
        return brandEntity;
    }

}

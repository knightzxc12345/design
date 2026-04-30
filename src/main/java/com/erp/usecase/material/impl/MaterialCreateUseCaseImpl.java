package com.erp.usecase.material.impl;

import com.erp.controller.material.request.MaterialCreateRequest;
import com.erp.entity.MaterialEntity;
import com.erp.service.MaterialService;
import com.erp.usecase.material.MaterialCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialCreateUseCaseImpl implements MaterialCreateUseCase {

    private final MaterialService materialService;

    @Override
    public void create(MaterialCreateRequest request) {
        MaterialEntity materialEntity = MaterialEntity.builder()
                .supplierUuid(request.supplierUuid())
                .name(request.name())
                .code(request.code())
                .spec(request.spec())
                .unit(request.unit())
                .cost(request.cost())
                .remark(request.remark())
                .build();
        materialService.create(materialEntity);
    }

}

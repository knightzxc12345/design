package com.erp.usecase.material.impl;

import com.erp.controller.material.request.MaterialEditRequest;
import com.erp.entity.MaterialEntity;
import com.erp.service.MaterialService;
import com.erp.usecase.material.MaterialEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialEditUseCaseImpl implements MaterialEditUseCase {

    private final MaterialService materialService;

    @Override
    public void edit(UUID uuid, MaterialEditRequest request) {
        MaterialEntity materialEntity = materialService.findByUuid(uuid);
        materialEntity = init(materialEntity, request);
        materialService.edit(materialEntity);
    }

    private MaterialEntity init(MaterialEntity materialEntity, MaterialEditRequest request){
        materialEntity.setSupplierUuid(request.supplierUuid());
        materialEntity.setName(request.name());
        materialEntity.setCode(request.code());
        materialEntity.setSpec(request.spec());
        materialEntity.setUnit(request.unit());
        materialEntity.setCost(request.cost());
        materialEntity.setStatus(request.status());
        return materialEntity;
    }

}

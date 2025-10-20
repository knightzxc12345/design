package com.design.usecase.item.impl;

import com.design.controller.item.request.ItemEditRequest;
import com.design.entity.ItemEntity;
import com.design.entity.SupplierEntity;
import com.design.service.ItemService;
import com.design.service.SupplierService;
import com.design.usecase.item.ItemEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemEditUseCaseImpl implements ItemEditUseCase {

    private final ItemService itemService;

    private final SupplierService supplierService;

    @Transactional
    @Override
    public void edit(String uuid, ItemEditRequest request) {
        ItemEntity itemEntity = itemService.findByUuid(uuid);
        itemEntity = init(itemEntity, request);
        itemService.edit(itemEntity);
    }

    private ItemEntity init(ItemEntity itemEntity, ItemEditRequest request){
        SupplierEntity supplierEntity = supplierService.findByUuid(request.supplierUuid());
        itemEntity.setGeneralTerm(request.generalTerm());
        itemEntity.setName(request.name());
        itemEntity.setDimension(request.dimension());
        itemEntity.setDescription(request.description());
        itemEntity.setUnit(request.unit());
        itemEntity.setPrice(request.price());
        itemEntity.setSupplier(supplierEntity);
        itemEntity.setStatus(request.status());
        return itemEntity;
    }

}

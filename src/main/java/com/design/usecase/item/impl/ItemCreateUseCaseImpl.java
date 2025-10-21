package com.design.usecase.item.impl;

import com.design.controller.item.request.ItemCreateRequest;
import com.design.entity.ItemEntity;
import com.design.entity.SupplierEntity;
import com.design.service.ItemService;
import com.design.service.SupplierService;
import com.design.usecase.item.ItemCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemCreateUseCaseImpl implements ItemCreateUseCase {

    private final ItemService itemService;

    private final SupplierService supplierService;

    @Transactional
    @Override
    public void create(ItemCreateRequest request) {
        ItemEntity itemEntity = init(request);
        itemService.create(itemEntity);
    }

    private ItemEntity init(ItemCreateRequest request){
        SupplierEntity supplierEntity = supplierService.findByUuid(request.supplierUuid());
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setGeneralTerm(request.generalTerm());
        itemEntity.setName(request.name());
        itemEntity.setDimension(request.dimension());
        itemEntity.setDescription(request.description());
        itemEntity.setUnit(request.unit());
        itemEntity.setPrice(request.price());
        itemEntity.setSupplier(supplierEntity);
        return itemEntity;
    }

}

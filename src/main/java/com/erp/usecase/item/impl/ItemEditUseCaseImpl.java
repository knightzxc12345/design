package com.erp.usecase.item.impl;

import com.erp.controller.item.request.ItemEditRequest;
import com.erp.entity.ItemEntity;
import com.erp.service.ItemService;
import com.erp.usecase.item.ItemEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemEditUseCaseImpl implements ItemEditUseCase {

    private final ItemService itemService;

    @Override
    public void edit(UUID uuid, ItemEditRequest request) {
        ItemEntity itemEntity = itemService.findByUuid(uuid);
        itemEntity = init(itemEntity, request);
        itemService.edit(itemEntity);
    }

    private ItemEntity init(ItemEntity itemEntity, ItemEditRequest request){
        itemEntity.setProductUuid(request.productUuid());
        itemEntity.setSkuCode(request.skuCode());
        itemEntity.setSpec(request.spec());
        itemEntity.setPrice(request.price());
        itemEntity.setCost(request.cost());
        return itemEntity;
    }

}

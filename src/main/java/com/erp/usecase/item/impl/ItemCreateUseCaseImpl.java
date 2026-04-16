package com.erp.usecase.item.impl;

import com.erp.controller.item.request.ItemCreateRequest;
import com.erp.entity.ItemEntity;
import com.erp.service.ItemService;
import com.erp.usecase.item.ItemCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCreateUseCaseImpl implements ItemCreateUseCase {

    private final ItemService itemService;

    @Override
    public void create(ItemCreateRequest request) {
        ItemEntity itemEntity = ItemEntity.builder()
                .productUuid(request.productUuid())
                .skuCode(request.skuCode())
                .spec(request.spec())
                .price(request.price())
                .cost(request.cost())
                .build();
        itemService.create(itemEntity);
    }

}

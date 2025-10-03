package com.design.usecase.item.impl;

import com.design.entity.ItemEntity;
import com.design.service.ItemService;
import com.design.usecase.item.ItemDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemDeleteUseCaseImpl implements ItemDeleteUseCase {

    private final ItemService itemService;

    @Override
    public void delete(String uuid) {
        ItemEntity itemEntity = itemService.findByUuid(uuid);
        itemService.delete(itemEntity);
    }

}

package com.design.usecase.item.impl;

import com.design.entity.ItemEntity;
import com.design.service.ItemService;
import com.design.usecase.item.ItemDeleteUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemDeleteUseCaseImpl implements ItemDeleteUseCase {

    private final ItemService itemService;

    @Transactional
    @Override
    public void delete(String uuid) {
        ItemEntity itemEntity = itemService.findByUuid(uuid);
        ImageUtil.deleteImage(itemEntity.getImageUrl());
        itemService.delete(itemEntity);
    }

}

package com.erp.usecase.item.impl;

import com.erp.service.ItemService;
import com.erp.usecase.item.ItemDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemDeleteUseCaseImpl implements ItemDeleteUseCase {

    private final ItemService itemService;

    @Override
    public void delete(UUID uuid) {
        itemService.delete(uuid);
    }

}

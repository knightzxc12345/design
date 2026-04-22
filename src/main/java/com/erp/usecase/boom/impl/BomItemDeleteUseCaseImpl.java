package com.erp.usecase.boom.impl;

import com.erp.service.BomItemService;
import com.erp.usecase.boom.BomItemDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BomItemDeleteUseCaseImpl implements BomItemDeleteUseCase {

    private final BomItemService bomItemService;

    @Override
    public void delete(UUID uuid) {
        bomItemService.delete(uuid);
    }

}

package com.erp.usecase.boom.impl;

import com.erp.service.BomItemService;
import com.erp.service.BomService;
import com.erp.usecase.boom.BomDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BomDeleteUseCaseImpl implements BomDeleteUseCase {

    private final BomService bomService;

    private final BomItemService bomItemService;

    @Override
    public void delete(UUID uuid) {
        bomService.delete(uuid);
        // 刪除bom明細清單
        bomItemService.deleteByBomUuid(uuid);
    }

}

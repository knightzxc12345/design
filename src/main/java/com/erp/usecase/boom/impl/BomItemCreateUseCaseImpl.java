package com.erp.usecase.boom.impl;

import com.erp.controller.bom.request.BomItemCreateRequest;
import com.erp.entity.BomEntity;
import com.erp.entity.BomItemEntity;
import com.erp.service.BomItemService;
import com.erp.service.BomService;
import com.erp.usecase.boom.BomItemCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BomItemCreateUseCaseImpl implements BomItemCreateUseCase {

    private final BomService bomService;

    private final BomItemService bomItemService;

    @Override
    public void create(BomItemCreateRequest request) {
        // 檢核bom
        BomEntity bomEntity = bomService.findByUuid(request.bomUuid());
        // TODO 檢核材料
        BomItemEntity bomItemEntity = BomItemEntity.builder()
                .bomUuid(request.bomUuid())
                .materialUuid(request.materialUuid())
                .quantity(request.quantity())
                .build();
        bomItemService.create(bomItemEntity);
    }

}

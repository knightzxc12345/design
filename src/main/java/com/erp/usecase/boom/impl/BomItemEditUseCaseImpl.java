package com.erp.usecase.boom.impl;

import com.erp.controller.bom.request.BomItemEditRequest;
import com.erp.entity.BomEntity;
import com.erp.entity.BomItemEntity;
import com.erp.service.BomItemService;
import com.erp.service.BomService;
import com.erp.usecase.boom.BomItemEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BomItemEditUseCaseImpl implements BomItemEditUseCase {

    private final BomService bomService;

    private final BomItemService bomItemService;

    @Override
    public void edit(UUID uuid, BomItemEditRequest request) {
        BomItemEntity bomItemEntity = bomItemService.findByUuid(uuid);
        // 檢核bom
        BomEntity bomEntity = bomService.findByUuid(request.bomUuid());
        // TODO 檢核材料
        bomItemEntity = init(bomItemEntity, request);
        bomItemService.edit(bomItemEntity);
    }

    private BomItemEntity init(BomItemEntity bomItemEntity, BomItemEditRequest request){
        bomItemEntity.setBomUuid(request.bomUuid());
        bomItemEntity.setMaterialUuid(request.materialUuid());
        bomItemEntity.setQuantity(request.quantity());
        return bomItemEntity;
    }

}

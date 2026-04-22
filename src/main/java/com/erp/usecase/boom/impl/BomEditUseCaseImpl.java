package com.erp.usecase.boom.impl;

import com.erp.controller.bom.request.BomEditRequest;
import com.erp.entity.BomEntity;
import com.erp.service.BomService;
import com.erp.usecase.boom.BomEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BomEditUseCaseImpl implements BomEditUseCase {

    private final BomService bomService;

    @Override
    public void edit(UUID uuid, BomEditRequest request) {
        BomEntity bomEntity = bomService.findByUuid(uuid);
        bomEntity = init(bomEntity, request);
        bomService.edit(bomEntity);
    }

    private BomEntity init(BomEntity bomEntity, BomEditRequest request){
        bomEntity.setItemUuid(request.itemUuid());
        bomEntity.setVersion(request.version());
        bomEntity.setStatus(request.status());
        return bomEntity;
    }

}

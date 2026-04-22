package com.erp.usecase.boom.impl;

import com.erp.controller.bom.request.BomCreateRequest;
import com.erp.entity.BomEntity;
import com.erp.service.BomService;
import com.erp.usecase.boom.BomCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BomCreateUseCaseImpl implements BomCreateUseCase {

    private final BomService bomService;

    @Override
    public void create(BomCreateRequest request) {
        BomEntity bomEntity = BomEntity.builder()
                .itemUuid(request.itemUuid())
                .version(request.version())
                .build();
        bomService.create(bomEntity);
    }

}

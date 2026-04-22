package com.erp.usecase.boom.impl;

import com.erp.controller.bom.response.BomItemFindAllResponse;
import com.erp.controller.bom.response.BomItemFindResponse;
import com.erp.entity.BomItemEntity;
import com.erp.service.BomItemService;
import com.erp.usecase.boom.BomItemFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BomItemFindUseCaseImpl implements BomItemFindUseCase {

    private final BomItemService bomItemService;

    @Override
    public BomItemFindResponse findDetail(UUID uuid) {
        BomItemEntity bomItemEntity = bomItemService.findByUuid(uuid);
        return new BomItemFindResponse(
                bomItemEntity.getUuid(),
                bomItemEntity.getBomUuid(),
                bomItemEntity.getMaterialUuid(),
                bomItemEntity.getQuantity()
        );
    }

    @Override
    public List<BomItemFindAllResponse> findAll(UUID bomUuid) {
        List<BomItemEntity> bomItemEntities = bomItemService.findAll(bomUuid);
        return formatList(bomItemEntities);
    }

    private List<BomItemFindAllResponse> formatList(List<BomItemEntity> bomItemEntities){
        if(null == bomItemEntities || bomItemEntities.isEmpty()){
            return List.of();
        }
        return bomItemEntities.stream()
                .map(bomItemEntity -> new BomItemFindAllResponse(
                        bomItemEntity.getUuid(),
                        bomItemEntity.getBomUuid(),
                        bomItemEntity.getMaterialUuid(),
                        bomItemEntity.getQuantity()
                ))
                .toList();
    }

}

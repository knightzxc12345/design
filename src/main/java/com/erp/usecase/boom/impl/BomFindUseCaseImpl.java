package com.erp.usecase.boom.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.bom.request.BomFindRequest;
import com.erp.controller.bom.request.BomPageRequest;
import com.erp.controller.bom.response.BomFindAllResponse;
import com.erp.controller.bom.response.BomFindResponse;
import com.erp.entity.BomEntity;
import com.erp.entity.BomItemEntity;
import com.erp.service.BomItemService;
import com.erp.service.BomService;
import com.erp.usecase.boom.BomFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BomFindUseCaseImpl implements BomFindUseCase {

    private final BomService bomService;

    private final BomItemService bomItemService;

    @Override
    public BomFindResponse findDetail(UUID uuid) {
        BomEntity bomEntity = bomService.findByUuid(uuid);
        List<BomItemEntity> bomItemEntities = bomItemService.findAll(uuid);
        return new BomFindResponse(
                bomEntity.getUuid(),
                bomEntity.getItemUuid(),
                bomEntity.getVersion(),
                bomEntity.getStatus(),
                bomItemEntities.stream()
                        .map(bomItemEntity -> new BomFindResponse.Item(
                                bomItemEntity.getUuid(),
                                bomItemEntity.getMaterialUuid(),
                                bomItemEntity.getQuantity()
                        ))
                        .toList()
        );
    }

    @Override
    public List<BomFindAllResponse> findAll(UUID itemUuid, BomFindRequest request) {
        List<BomEntity> bomEntities = bomService.findAll(
                itemUuid,
                request.keyword(),
                request.status()
        );
        return formatList(bomEntities);
    }

    @Override
    public PageResponse<BomFindAllResponse> findPage(UUID itemUuid, BomPageRequest request) {
        Page<BomEntity> bomEntityPage = bomService.findPage(
                PageRequest.of(request.page(), request.size()),
                itemUuid,
                request.keyword(),
                request.status()
        );
        return formatPage(bomEntityPage);
    }

    private List<BomFindAllResponse> formatList(List<BomEntity> bomEntities){
        if(null == bomEntities || bomEntities.isEmpty()){
            return List.of();
        }
        return bomEntities.stream()
                .map(bomEntity -> new BomFindAllResponse(
                        bomEntity.getUuid(),
                        bomEntity.getItemUuid(),
                        bomEntity.getVersion(),
                        bomEntity.getStatus()
                ))
                .toList();
    }

    private PageResponse<BomFindAllResponse> formatPage(Page<BomEntity> bomEntityPage){
        List<BomFindAllResponse> responses = formatList(bomEntityPage.getContent());
        return new PageResponse<BomFindAllResponse>(
                bomEntityPage.getNumber(),
                bomEntityPage.getSize(),
                bomEntityPage.getTotalElements(),
                bomEntityPage.getTotalPages(),
                responses
        );
    }

}

package com.erp.usecase.item.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.item.request.ItemFindRequest;
import com.erp.controller.item.request.ItemPageRequest;
import com.erp.controller.item.response.ItemFindAllResponse;
import com.erp.controller.item.response.ItemFindResponse;
import com.erp.entity.ItemEntity;
import com.erp.service.ItemService;
import com.erp.usecase.item.ItemFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemFindUseCaseImpl implements ItemFindUseCase {

    private final ItemService itemService;

    @Transactional(readOnly = true)
    @Override
    public ItemFindResponse findDetail(UUID uuid) {
        ItemEntity itemEntity = itemService.findByUuid(uuid);
        return new ItemFindResponse(
                itemEntity.getUuid(),
                itemEntity.getProductUuid(),
                itemEntity.getSkuCode(),
                itemEntity.getSpec(),
                itemEntity.getPrice(),
                itemEntity.getCost(),
                itemEntity.getStatus()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemFindAllResponse> findAll(UUID productUuid, ItemFindRequest request) {
        // TODO 檢查產品
        List<ItemEntity> itemEntities = itemService.findAllByProductUuid(
                productUuid,
                request.keyword(),
                request.status()
        );
        return formatList(itemEntities);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ItemFindAllResponse> findPage(UUID productUuid, ItemPageRequest request) {
        Page<ItemEntity> itemEntityPage = itemService.findPageByProductUuid(
                PageRequest.of(request.page(), request.size()),
                productUuid,
                request.keyword(),
                request.status()
        );
        return formatPage(itemEntityPage);
    }

    private List<ItemFindAllResponse> formatList(List<ItemEntity> itemEntities){
        if(null == itemEntities || itemEntities.isEmpty()){
            return List.of();
        }
        return itemEntities.stream()
                .map(itemEntity -> new ItemFindAllResponse(
                    itemEntity.getUuid(),
                    itemEntity.getProductUuid(),
                    itemEntity.getSkuCode(),
                    itemEntity.getSpec(),
                    itemEntity.getPrice(),
                    itemEntity.getCost(),
                    itemEntity.getStatus()
                ))
                .toList();
    }

    private PageResponse<ItemFindAllResponse> formatPage(Page<ItemEntity> itemEntityPage){
        List<ItemFindAllResponse> responses = formatList(itemEntityPage.getContent());
        return new PageResponse<ItemFindAllResponse>(
                itemEntityPage.getNumber(),
                itemEntityPage.getSize(),
                itemEntityPage.getTotalElements(),
                itemEntityPage.getTotalPages(),
                responses
        );
    }

}

package com.design.usecase.item.impl;

import com.design.controller.common.response.PageResponse;
import com.design.controller.item.request.ItemFindRequest;
import com.design.controller.item.request.ItemPageRequest;
import com.design.controller.item.response.ItemFindAllResponse;
import com.design.controller.item.response.ItemFindResponse;
import com.design.controller.item.response.ItemPageResponse;
import com.design.entity.ItemEntity;
import com.design.service.ItemService;
import com.design.usecase.item.ItemFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemFindUseCaseImpl implements ItemFindUseCase {

    @Value("${server.servlet.context-path}")
    private String server;

    private final ItemService itemService;

    @Override
    public ItemFindResponse findDetail(String uuid) {
        ItemEntity itemEntity = itemService.findByUuid(uuid);
        return new ItemFindResponse(
                itemEntity.getName(),
                itemEntity.getCode(),
                itemEntity.getDimension(),
                itemEntity.getDescription(),
                String.format("%s%s", server, itemEntity.getImageUrl()),
                itemEntity.getUnit(),
                itemEntity.getPrice(),
                itemEntity.getSupplier().getUuid(),
                itemEntity.getSupplier().getName(),
                itemEntity.getStatus()
        );
    }

    @Override
    public List<ItemFindAllResponse> findAll(ItemFindRequest request) {
        List<ItemEntity> itemEntities = itemService.findAll(request.keyword());
        return formatList(itemEntities);
    }

    @Override
    public ItemPageResponse findByPage(ItemPageRequest request) {
        Page<ItemEntity> itemEntityPage = itemService.findByPage(
                request.keyword(),
                PageRequest.of(request.page(), request.size())
        );
        return formatPage(itemEntityPage);
    }

    private List<ItemFindAllResponse> formatList(List<ItemEntity> itemEntities){
        List<ItemFindAllResponse> responses = new ArrayList<>();
        if(null == itemEntities || itemEntities.isEmpty()){
            return responses;
        }
        for(ItemEntity itemEntity : itemEntities){
            responses.add(new ItemFindAllResponse(
                    itemEntity.getUuid(),
                    itemEntity.getName(),
                    itemEntity.getCode(),
                    itemEntity.getDimension(),
                    String.format("%s%s", server, itemEntity.getImageUrl()),
                    itemEntity.getUnit(),
                    itemEntity.getSupplier().getName(),
                    itemEntity.getStatus()
            ));
        }
        return responses;
    }

    private ItemPageResponse formatPage(Page<ItemEntity> itemEntityPage){
        List<ItemFindAllResponse> responses = formatList(itemEntityPage.getContent());
        return new ItemPageResponse(
                new PageResponse(
                        itemEntityPage.getNumber(),
                        itemEntityPage.getSize(),
                        itemEntityPage.getTotalPages()
                ),
                responses
        );
    }

}

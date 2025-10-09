package com.design.usecase.item.impl;

import com.design.controller.item.request.ItemEditRequest;
import com.design.entity.ItemEntity;
import com.design.entity.SupplierEntity;
import com.design.service.ItemService;
import com.design.service.SupplierService;
import com.design.usecase.item.ItemEditUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemEditUseCaseImpl implements ItemEditUseCase {

    private final ItemService itemService;

    private final SupplierService supplierService;

    @Override
    public void edit(String uuid, ItemEditRequest request, MultipartFile file) {
        ItemEntity itemEntity = itemService.findByUuid(uuid);
        String imageUrl = ImageUtil.uploadImage(file);
        if(null != imageUrl){
            ImageUtil.deleteImage(itemEntity.getImageUrl());
        }
        itemEntity = init(itemEntity, request, imageUrl);
        itemService.edit(itemEntity);
    }

    private ItemEntity init(ItemEntity itemEntity, ItemEditRequest request, String imageUrl){
        SupplierEntity supplierEntity = supplierService.findByUuid(request.supplierUuid());
        itemEntity.setName(request.name());
        itemEntity.setCode(request.code());
        itemEntity.setDimension(request.dimension());
        itemEntity.setDescription(request.description());
        itemEntity.setImageUrl(null == imageUrl ? itemEntity.getImageUrl() : imageUrl);
        itemEntity.setUnit(request.unit());
        itemEntity.setPrice(request.price());
        itemEntity.setSupplier(supplierEntity);
        itemEntity.setStatus(request.status());
        return itemEntity;
    }

}

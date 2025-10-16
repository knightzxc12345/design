package com.design.usecase.item.impl;

import com.design.base.common.Common;
import com.design.controller.item.request.ItemCreateRequest;
import com.design.entity.ItemEntity;
import com.design.entity.SupplierEntity;
import com.design.service.ItemService;
import com.design.service.SupplierService;
import com.design.usecase.item.ItemCreateUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemCreateUseCaseImpl implements ItemCreateUseCase {

    private final ItemService itemService;

    private final SupplierService supplierService;

    @Transactional
    @Override
    public void create(ItemCreateRequest request, MultipartFile file) {
        String imageUrl = ImageUtil.uploadImage(Common.IMAGE_PATH_ITEM, file);
        ItemEntity itemEntity = init(request, imageUrl);
        itemService.create(itemEntity);
    }

    private ItemEntity init(ItemCreateRequest request, String imageUrl){
        SupplierEntity supplierEntity = supplierService.findByUuid(request.supplierUuid());
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName(request.name());
        itemEntity.setCode(request.code());
        itemEntity.setDimension(request.dimension());
        itemEntity.setDescription(request.description());
        itemEntity.setImageUrl(imageUrl);
        itemEntity.setUnit(request.unit());
        itemEntity.setPrice(request.price());
        itemEntity.setSupplier(supplierEntity);
        return itemEntity;
    }

}

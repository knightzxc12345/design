package com.design.usecase.product.impl;

import com.design.controller.product.request.ProductEditRequest;
import com.design.entity.ItemEntity;
import com.design.entity.ProductEntity;
import com.design.service.ItemService;
import com.design.service.ProductService;
import com.design.usecase.product.ProductEditUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductEditUseCaseImpl implements ProductEditUseCase {

    private final ProductService productService;

    private final ItemService itemService;

    @Override
    public void edit(String uuid, ProductEditRequest request, MultipartFile file) {
        ProductEntity productEntity = productService.findByUuid(uuid);
        String imageUrl = ImageUtil.uploadImage(file);
        if(null != imageUrl){
            ImageUtil.deleteImage(productEntity.getImageUrl());
        }
        productEntity = init(productEntity, request, imageUrl);
        productService.edit(productEntity);
    }

    private ProductEntity init(ProductEntity productEntity, ProductEditRequest request, String imageUrl){
        List<ItemEntity> itemEntities = itemService.findAllWithUuids(request.itemUuids());
        productEntity.setName(request.name());
        productEntity.setCode(request.code());
        productEntity.setDimension(request.dimension());
        productEntity.setDescription(request.description());
        productEntity.setImageUrl(null == imageUrl ? productEntity.getImageUrl() : imageUrl);
        productEntity.setUnit(request.unit());
        productEntity.setPrice(request.price());
        productEntity.setItems(itemEntities);
        productEntity.setStatus(request.status());
        return productEntity;
    }

}

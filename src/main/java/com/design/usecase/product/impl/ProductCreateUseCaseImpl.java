package com.design.usecase.product.impl;

import com.design.controller.product.request.ProductCreateRequest;
import com.design.entity.ItemEntity;
import com.design.entity.ProductEntity;
import com.design.service.ItemService;
import com.design.service.ProductService;
import com.design.usecase.product.ProductCreateUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCreateUseCaseImpl implements ProductCreateUseCase {

    private final ProductService productService;

    private final ItemService itemService;

    @Override
    public void create(ProductCreateRequest request, MultipartFile file) {
        String imageUrl = ImageUtil.uploadImage(file);
        ProductEntity productEntity = init(request, imageUrl);
        productService.create(productEntity);
    }

    private ProductEntity init(ProductCreateRequest request, String imageUrl){
        List<ItemEntity> itemEntities = itemService.findAllWithUuids(request.itemUuids());
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.name());
        productEntity.setCode(request.code());
        productEntity.setDimension(request.dimension());
        productEntity.setDescription(request.description());
        productEntity.setImageUrl(imageUrl);
        productEntity.setUnit(request.unit());
        productEntity.setPrice(request.price());
        productEntity.setItems(itemEntities);
        return productEntity;
    }

}

package com.design.usecase.product.impl;

import com.design.base.api.ItemCode;
import com.design.base.common.Common;
import com.design.controller.product.request.ProductEditRequest;
import com.design.entity.ItemEntity;
import com.design.entity.ProductEntity;
import com.design.entity.ProductItemEntity;
import com.design.handler.BusinessException;
import com.design.service.ItemService;
import com.design.service.ProductItemService;
import com.design.service.ProductService;
import com.design.usecase.product.ProductEditUseCase;
import com.design.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductEditUseCaseImpl implements ProductEditUseCase {

    private final ProductService productService;

    private final ProductItemService productItemService;

    private final ItemService itemService;

    @Override
    public void edit(String uuid, ProductEditRequest request, MultipartFile file) {
        // 查詢產品
        ProductEntity productEntity = productService.findByUuid(uuid);
        // 圖片處理
        String imageUrl = productEntity.getImageUrl();
        if (file != null && !file.isEmpty()) {
            imageUrl = ImageUtil.uploadImage(Common.IMAGE_PATH_PRODUCT, file);
            ImageUtil.deleteImage(productEntity.getImageUrl());
        }
        // 查詢前端品項對應 ItemEntity
        List<String> uuids = request.items().stream()
                .map(ProductEditRequest.Item::uuid)
                .toList();
        List<ItemEntity> itemEntities = itemService.findAllWithUuids(uuids);
        // 初始化或更新 ProductItemEntity
        List<ProductItemEntity> productItemEntities = initProductItem(itemEntities, request);
        // 存 ProductItemEntity（生成 UUID 或更新 quantity）
        productItemService.createAll(productItemEntities);
        // 初始化 ProductEntity
        productEntity = initProduct(productEntity, request, productItemEntities, imageUrl);
        // 存 ProductEntity
        productService.edit(productEntity);
    }

    /**
     * 初始化 ProductEntity
     */
    private ProductEntity initProduct(ProductEntity productEntity, ProductEditRequest request,
                                      List<ProductItemEntity> productItemEntities, String imageUrl) {
        productEntity.setName(request.name());
        productEntity.setCode(request.code());
        productEntity.setDimension(request.dimension());
        productEntity.setDescription(request.description());
        productEntity.setImageUrl(imageUrl);
        productEntity.setUnit(request.unit());
        productEntity.setPrice(request.price());
        productEntity.setItems(productItemEntities);
        productEntity.setStatus(request.status());
        return productEntity;
    }

    private List<ProductItemEntity> initProductItem(List<ItemEntity> itemEntities, ProductEditRequest request) {
        Map<String, ItemEntity> itemMap = itemEntities.stream()
                .collect(Collectors.toMap(ItemEntity::getUuid, e -> e));

        return request.items().stream()
                .map(reqItem -> {
                    ItemEntity matchedItem = itemMap.get(reqItem.uuid());
                    if (matchedItem == null) {
                        throw new BusinessException(ItemCode.NOT_EXISTS);
                    }
                    ProductItemEntity productItem = new ProductItemEntity();
                    productItem.setItem(matchedItem);
                    productItem.setQuantity(reqItem.quantity());
                    return productItem;
                })
                .toList();
    }

}

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
import com.design.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
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
        List<ProductItemEntity> oldProductItemEntities = productItemService.findAll(productEntity.getUuid());
        BigDecimal costPrice = BigDecimal.ZERO;
        // 圖片處理
        String imageUrl = productEntity.getImageUrl();
        if (file != null && !file.isEmpty()) {
            ImageUtil.deleteImage(productEntity.getImageUrl());
            imageUrl = ImageUtil.uploadImage(Common.IMAGE_PATH_PRODUCT, file);
        }
        // 轉換 items
        List<ProductEditRequest.Item> items = JsonUtil.get(request.items(), new TypeReference<List<ProductEditRequest.Item>>() {});
        List<String> uuids = items.stream().map(ProductEditRequest.Item::uuid).toList();
        List<ItemEntity> itemEntities = itemService.findAllWithUuids(uuids);

        // 初始化 ProductItemEntity
        List<ProductItemEntity> newProductItemEntities = initProductItem(itemEntities, items);
        for (ProductItemEntity pi : newProductItemEntities) {
            pi.setProduct(productEntity);
            costPrice = costPrice.add(pi.getItem().getPrice().multiply(BigDecimal.valueOf(pi.getQuantity())));
        }
        productItemService.deleteAll(oldProductItemEntities);
        productItemService.createAll(newProductItemEntities);
        // 初始化 ProductEntity
        productEntity = initProduct(productEntity, request, newProductItemEntities, imageUrl);
        productEntity.setItems(newProductItemEntities);
        productEntity.setCostPrice(costPrice);
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

    private List<ProductItemEntity> initProductItem(List<ItemEntity> itemEntities, List<ProductEditRequest.Item> items) {
        Map<String, ItemEntity> itemMap = itemEntities.stream()
                .collect(Collectors.toMap(ItemEntity::getUuid, e -> e));
        return items.stream()
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

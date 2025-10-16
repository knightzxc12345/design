package com.design.usecase.product.impl;

import com.design.base.api.ItemCode;
import com.design.base.common.Common;
import com.design.controller.product.request.ProductCreateRequest;
import com.design.entity.ItemEntity;
import com.design.entity.ProductEntity;
import com.design.entity.ProductItemEntity;
import com.design.handler.BusinessException;
import com.design.service.ItemService;
import com.design.service.ProductItemService;
import com.design.service.ProductService;
import com.design.usecase.product.ProductCreateUseCase;
import com.design.utils.ImageUtil;
import com.design.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCreateUseCaseImpl implements ProductCreateUseCase {

    private final ProductService productService;

    private final ProductItemService productItemService;

    private final ItemService itemService;

    @Transactional
    @Override
    public void create(ProductCreateRequest request, MultipartFile file) {
        // 先上傳圖片
        String imageUrl = ImageUtil.uploadImage(Common.IMAGE_PATH_PRODUCT, file);

        BigDecimal costPrice = BigDecimal.ZERO;

        // 先初始化 ProductEntity（不關聯 ProductItem）
        ProductEntity productEntity = initProduct(request, null, imageUrl);
        productService.create(productEntity);

        // 轉換 items
        List<ProductCreateRequest.Item> items = JsonUtil.get(request.items(), new TypeReference<List<ProductCreateRequest.Item>>() {});
        List<String> uuids = items.stream().map(ProductCreateRequest.Item::uuid).toList();
        List<ItemEntity> itemEntities = itemService.findAllWithUuids(uuids);

        // 初始化 ProductItemEntity，並關聯 product
        List<ProductItemEntity> productItemEntities = initProductItem(itemEntities, items);
        for (ProductItemEntity pi : productItemEntities) {
            pi.setProduct(productEntity);
            costPrice = costPrice.add(pi.getItem().getPrice().multiply(BigDecimal.valueOf(pi.getQuantity())));
        }

        // 存 ProductItemEntity
        productItemService.createAll(productItemEntities);

        // 如果 ProductEntity 有 bidirectional 關聯，補上 productItems
        productEntity.setItems(productItemEntities);
        productEntity.setCostPrice(costPrice);
        productService.edit(productEntity);
    }

    /**
     * 初始化 ProductEntity
     */
    private ProductEntity initProduct(ProductCreateRequest request, List<ProductItemEntity> productItemEntities, String imageUrl) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.name());
        productEntity.setCode(request.code());
        productEntity.setDimension(request.dimension());
        productEntity.setDescription(request.description());
        productEntity.setImageUrl(imageUrl);
        productEntity.setUnit(request.unit());
        productEntity.setCostPrice(new BigDecimal(0));
        productEntity.setPrice(request.price());
        productEntity.setItems(productItemEntities);
        return productEntity;
    }

    private List<ProductItemEntity> initProductItem(List<ItemEntity> itemEntities, List<ProductCreateRequest.Item> items) {
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

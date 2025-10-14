package com.design.usecase.product.impl;

import com.design.base.common.Common;
import com.design.controller.common.response.PageResponse;
import com.design.controller.product.request.ProductFindRequest;
import com.design.controller.product.request.ProductPageRequest;
import com.design.controller.product.response.ProductFindAllResponse;
import com.design.controller.product.response.ProductFindResponse;
import com.design.controller.product.response.ProductItem;
import com.design.controller.product.response.ProductPageResponse;
import com.design.entity.ProductEntity;
import com.design.service.ProductService;
import com.design.usecase.product.ProductFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFindUseCaseImpl implements ProductFindUseCase {

    @Value("${server.servlet.context-path}")
    private String server;

    private final ProductService productService;

    @Override
    public ProductFindResponse findDetail(String uuid) {
        ProductEntity productEntity = productService.findByUuid(uuid);
        List<ProductItem> productItems = productEntity.getItems().stream()
                .map(pi -> new ProductItem(
                        pi.getUuid(),
                        pi.getItem().getName(),
                        pi.getItem().getSupplier().getUuid(),
                        pi.getItem().getSupplier().getName(),
                        pi.getQuantity(),
                        pi.getItem().getPrice()
                ))
                .toList();
        return new ProductFindResponse(
                productEntity.getName(),
                productEntity.getCode(),
                productEntity.getDescription(),
                productEntity.getDimension(),
                productEntity.getUnit(),
                productEntity.getCostPrice(),
                productEntity.getPrice(),
                String.format("%s%s", server, productEntity.getImageUrl()),
                productItems,
                productEntity.getStatus()
        );
    }

    @Override
    public List<ProductFindAllResponse> findAll(ProductFindRequest request) {
        List<ProductEntity> productEntities = productService.findAll(request.keyword());
        return formatList(productEntities);
    }

    @Override
    public ProductPageResponse findByPage(ProductPageRequest request) {
        Page<ProductEntity> productEntityPage = productService.findByPage(
                request.keyword(),
                PageRequest.of(request.page(), request.size())
        );
        return formatPage(productEntityPage);
    }

    private List<ProductFindAllResponse> formatList(List<ProductEntity> productEntities){
        if(productEntities == null || productEntities.isEmpty()){
            return List.of();
        }
        return productEntities.stream()
                .map(productEntity -> new ProductFindAllResponse(
                        productEntity.getUuid(),
                        productEntity.getName(),
                        productEntity.getCode(),
                        productEntity.getDimension(),
                        productEntity.getUnit(),
                        productEntity.getCostPrice(),
                        productEntity.getPrice(),
                        String.format("%s%s", server, productEntity.getImageUrl()),
                        productEntity.getStatus()
                ))
                .toList();
    }

    private ProductPageResponse formatPage(Page<ProductEntity> productEntityPage){
        List<ProductFindAllResponse> responses = formatList(productEntityPage.getContent());
        return new ProductPageResponse(
                new PageResponse(
                        productEntityPage.getNumber(),
                        productEntityPage.getSize(),
                        productEntityPage.getTotalPages()
                ),
                responses
        );
    }

}

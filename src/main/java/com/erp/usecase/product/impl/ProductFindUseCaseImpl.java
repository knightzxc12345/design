package com.erp.usecase.product.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.product.request.ProductFindRequest;
import com.erp.controller.product.request.ProductPageRequest;
import com.erp.controller.product.response.ProductFindAllResponse;
import com.erp.controller.product.response.ProductFindResponse;
import com.erp.entity.ProductEntity;
import com.erp.service.ProductService;
import com.erp.usecase.product.ProductFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductFindUseCaseImpl implements ProductFindUseCase {

    private final ProductService productService;

    @Transactional(readOnly = true)
    @Override
    public ProductFindResponse findDetail(UUID uuid) {
        ProductEntity productEntity = productService.findByUuid(uuid);
        return new ProductFindResponse(
                productEntity.getUuid(),
                productEntity.getBrandUuid(),
                productEntity.getCategoryUuid(),
                productEntity.getName(),
                productEntity.getCode(),
                productEntity.getDescription(),
                productEntity.getStatus()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductFindAllResponse> findAll(
            UUID brandUuid,
            UUID categoryUuid,
            ProductFindRequest request) {
        List<ProductEntity> productEntities = productService.findAllByBrandUuidAndCategoryUuid(
                brandUuid,
                categoryUuid,
                request.keyword(),
                request.status()
        );
        return formatList(productEntities);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ProductFindAllResponse> findPage(
            UUID brandUuid,
            UUID categoryUuid,
            ProductPageRequest request) {
        Page<ProductEntity> productEntityPage = productService.findPageByBrandUuidAndCategoryUuid(
                PageRequest.of(request.page(), request.size()),
                brandUuid,
                categoryUuid,
                request.keyword(),
                request.status()
        );
        return formatPage(productEntityPage);
    }

    private List<ProductFindAllResponse> formatList(List<ProductEntity> productEntities){
        if(null == productEntities || productEntities.isEmpty()){
            return List.of();
        }
        return productEntities.stream()
                .map(productEntity -> new ProductFindAllResponse(
                        productEntity.getUuid(),
                        productEntity.getBrandUuid(),
                        productEntity.getCategoryUuid(),
                        productEntity.getName(),
                        productEntity.getCode(),
                        productEntity.getDescription(),
                        productEntity.getStatus()
                ))
                .toList();
    }

    private PageResponse<ProductFindAllResponse> formatPage(Page<ProductEntity> productEntityPage){
        List<ProductFindAllResponse> responses = formatList(productEntityPage.getContent());
        return new PageResponse<ProductFindAllResponse>(
                productEntityPage.getNumber(),
                productEntityPage.getSize(),
                productEntityPage.getTotalElements(),
                productEntityPage.getTotalPages(),
                responses
        );
    }

}

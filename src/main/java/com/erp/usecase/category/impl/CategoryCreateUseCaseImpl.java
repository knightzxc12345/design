package com.erp.usecase.category.impl;

import com.erp.controller.category.request.CategoryCreateRequest;
import com.erp.entity.BrandEntity;
import com.erp.entity.CategoryEntity;
import com.erp.service.BrandService;
import com.erp.service.CategoryService;
import com.erp.usecase.category.CategoryCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCreateUseCaseImpl implements CategoryCreateUseCase {

    private final BrandService brandService;

    private final CategoryService categoryService;

    @Override
    public void create(CategoryCreateRequest request) {
        // 檢核品牌
        BrandEntity brandEntity = brandService.findByUuid(request.brandUuid());
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .brandUuid(request.brandUuid())
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .build();
        categoryService.create(categoryEntity);
    }

}

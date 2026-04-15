package com.erp.usecase.category.impl;

import com.erp.controller.category.request.CategoryEditRequest;
import com.erp.entity.BrandEntity;
import com.erp.entity.CategoryEntity;
import com.erp.service.BrandService;
import com.erp.service.CategoryService;
import com.erp.usecase.category.CategoryEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryEditUseCaseImpl implements CategoryEditUseCase {

    private final BrandService brandService;

    private final CategoryService categoryService;

    @Override
    public void edit(UUID uuid, CategoryEditRequest request) {
        // 檢核
        BrandEntity brandEntity = brandService.findByUuid(request.brandUuid());
        CategoryEntity categoryEntity = categoryService.findByUuid(uuid);
        categoryEntity = init(categoryEntity, request);
        categoryService.edit(categoryEntity);
    }

    private CategoryEntity init(CategoryEntity categoryEntity, CategoryEditRequest request){
        categoryEntity.setBrandUuid(request.brandUuid());
        categoryEntity.setName(request.name());
        categoryEntity.setCode(request.code());
        categoryEntity.setDescription(request.description());
        categoryEntity.setStatus(request.status());
        return categoryEntity;
    }

}

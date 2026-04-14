package com.erp.usecase.category.impl;

import com.erp.controller.category.request.CategoryCreateRequest;
import com.erp.entity.CategoryEntity;
import com.erp.service.CategoryService;
import com.erp.usecase.category.CategoryCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCreateUseCaseImpl implements CategoryCreateUseCase {

    private final CategoryService categoryService;

    @Override
    public void create(CategoryCreateRequest request) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .build();
        categoryService.create(categoryEntity);
    }

}

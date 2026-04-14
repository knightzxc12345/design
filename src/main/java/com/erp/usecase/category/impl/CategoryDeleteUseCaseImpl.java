package com.erp.usecase.category.impl;

import com.erp.service.CategoryService;
import com.erp.usecase.category.CategoryDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryDeleteUseCaseImpl implements CategoryDeleteUseCase {

    private final CategoryService categoryService;

    @Override
    public void delete(UUID uuid) {
        categoryService.delete(uuid);
    }

}

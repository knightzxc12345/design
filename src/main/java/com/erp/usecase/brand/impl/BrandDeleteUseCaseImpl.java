package com.erp.usecase.brand.impl;

import com.erp.service.BrandService;
import com.erp.service.CategoryService;
import com.erp.usecase.brand.BrandDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandDeleteUseCaseImpl implements BrandDeleteUseCase {

    private final BrandService brandService;

    private final CategoryService categoryService;

    @Override
    public void delete(UUID uuid) {
        brandService.delete(uuid);
        // 刪除所有種類
        categoryService.deleteAllByBrandUuid(uuid);
    }

}

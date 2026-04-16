package com.erp.usecase.category.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.category.request.CategoryFindRequest;
import com.erp.controller.category.request.CategoryPageRequest;
import com.erp.controller.category.response.CategoryFindAllResponse;
import com.erp.controller.category.response.CategoryFindResponse;
import com.erp.entity.BrandEntity;
import com.erp.entity.CategoryEntity;
import com.erp.service.BrandService;
import com.erp.service.CategoryService;
import com.erp.usecase.category.CategoryFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryFindUseCaseImpl implements CategoryFindUseCase {

    private final BrandService brandService;

    private final CategoryService categoryService;

    @Transactional(readOnly = true)
    @Override
    public CategoryFindResponse findDetail(UUID uuid) {
        CategoryEntity categoryEntity = categoryService.findByUuid(uuid);
        return new CategoryFindResponse(
                categoryEntity.getUuid(),
                categoryEntity.getBrandUuid(),
                categoryEntity.getName(),
                categoryEntity.getCode(),
                categoryEntity.getDescription(),
                categoryEntity.getStatus()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryFindAllResponse> findAll(UUID brandUuid, CategoryFindRequest request) {
        // 檢查品牌
        BrandEntity brandEntity = brandService.findByUuid(brandUuid);
        List<CategoryEntity> categoryEntities = categoryService.findAll(
                brandUuid,
                request.keyword(),
                request.status()
        );
        return formatList(categoryEntities);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<CategoryFindAllResponse> findPage(UUID brandUuid, CategoryPageRequest request) {
        Page<CategoryEntity> categoryEntityPage = categoryService.findPageByBrandUuid(
                PageRequest.of(request.page(), request.size()),
                brandUuid,
                request.keyword(),
                request.status()
        );
        return formatPage(categoryEntityPage);
    }

    private List<CategoryFindAllResponse> formatList(List<CategoryEntity> categoryEntities){
        if(null == categoryEntities || categoryEntities.isEmpty()){
            return List.of();
        }
        return categoryEntities.stream()
                .map(categoryEntity -> new CategoryFindAllResponse(
                    categoryEntity.getUuid(),
                    categoryEntity.getBrandUuid(),
                    categoryEntity.getName(),
                    categoryEntity.getCode(),
                    categoryEntity.getDescription(),
                    categoryEntity.getStatus()
                ))
                .toList();
    }

    private PageResponse<CategoryFindAllResponse> formatPage(Page<CategoryEntity> categoryEntityPage){
        List<CategoryFindAllResponse> responses = formatList(categoryEntityPage.getContent());
        return new PageResponse<CategoryFindAllResponse>(
                categoryEntityPage.getNumber(),
                categoryEntityPage.getSize(),
                categoryEntityPage.getTotalElements(),
                categoryEntityPage.getTotalPages(),
                responses
        );
    }

}

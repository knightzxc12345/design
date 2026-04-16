package com.erp.usecase.category;

import com.erp.base.response.PageResponse;
import com.erp.controller.category.request.CategoryFindRequest;
import com.erp.controller.category.request.CategoryPageRequest;
import com.erp.controller.category.response.CategoryFindAllResponse;
import com.erp.controller.category.response.CategoryFindResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryFindUseCase {

    CategoryFindResponse findDetail(UUID uuid);

    List<CategoryFindAllResponse> findAll(CategoryFindRequest request);

    PageResponse<CategoryFindAllResponse> findPage(CategoryPageRequest request);

}

package com.erp.usecase.category;

import com.erp.controller.category.request.CategoryEditRequest;

import java.util.UUID;

public interface CategoryEditUseCase {

    void edit(UUID uuid, CategoryEditRequest request);

}

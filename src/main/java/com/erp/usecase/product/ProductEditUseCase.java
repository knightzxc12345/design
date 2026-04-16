package com.erp.usecase.product;

import com.erp.controller.product.request.ProductEditRequest;

import java.util.UUID;

public interface ProductEditUseCase {

    void edit(UUID uuid, ProductEditRequest request);

}

package com.erp.usecase.brand;

import com.erp.controller.brand.request.BrandEditRequest;

import java.util.UUID;

public interface BrandEditUseCase {

    void edit(UUID uuid, BrandEditRequest request);

}

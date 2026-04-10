package com.erp.usecase.supplier;

import com.erp.controller.supplier.request.SupplierEditRequest;

import java.util.UUID;

public interface SupplierEditUseCase {

    void edit(UUID uuid, SupplierEditRequest request);

}

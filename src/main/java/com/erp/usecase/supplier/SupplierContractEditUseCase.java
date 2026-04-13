package com.erp.usecase.supplier;

import com.erp.controller.supplier.request.SupplierContractEditRequest;

import java.util.UUID;

public interface SupplierContractEditUseCase {

    void edit(UUID uuid, SupplierContractEditRequest request);

}

package com.erp.usecase.supplier;

import com.erp.controller.supplier.response.SupplierContractFindAllResponse;
import com.erp.controller.supplier.response.SupplierContractFindResponse;

import java.util.List;
import java.util.UUID;

public interface SupplierContractFindUseCase {

    SupplierContractFindResponse findDetail(UUID uuid);

    List<SupplierContractFindAllResponse> findAll(UUID supplierUuid);

}

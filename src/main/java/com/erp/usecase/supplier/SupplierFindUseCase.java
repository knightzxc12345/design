package com.erp.usecase.supplier;

import com.erp.base.response.PageResponse;
import com.erp.controller.supplier.request.SupplierFindRequest;
import com.erp.controller.supplier.request.SupplierPageRequest;
import com.erp.controller.supplier.response.SupplierFindAllResponse;
import com.erp.controller.supplier.response.SupplierFindResponse;

import java.util.List;
import java.util.UUID;

public interface SupplierFindUseCase {

    SupplierFindResponse findDetail(UUID uuid);

    List<SupplierFindAllResponse> findAll(SupplierFindRequest request);

    PageResponse<SupplierFindAllResponse> findPage(SupplierPageRequest request);

}

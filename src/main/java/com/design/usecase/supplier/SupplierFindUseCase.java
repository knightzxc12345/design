package com.design.usecase.supplier;

import com.design.controller.supplier.requst.SupplierFindRequest;
import com.design.controller.supplier.requst.SupplierPageRequest;
import com.design.controller.supplier.response.SupplierFindAllResponse;
import com.design.controller.supplier.response.SupplierFindResponse;
import com.design.controller.supplier.response.SupplierPageResponse;

import java.util.List;

public interface SupplierFindUseCase {

    SupplierFindResponse findDetail(String uuid);

    List<SupplierFindAllResponse> findAll(SupplierFindRequest request);

    SupplierPageResponse findByPage(SupplierPageRequest request);

}

package com.design.usecase.supplier;

import com.design.controller.supplier.requst.SupplierEditRequest;

import java.util.UUID;

public interface SupplierEditUseCase {

    void edit(String uuid, SupplierEditRequest request);

}

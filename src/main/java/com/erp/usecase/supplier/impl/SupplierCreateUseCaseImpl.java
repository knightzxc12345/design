package com.erp.usecase.supplier.impl;

import com.erp.controller.supplier.request.SupplierCreateRequest;
import com.erp.entity.SupplierEntity;
import com.erp.service.SupplierService;
import com.erp.usecase.supplier.SupplierCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierCreateUseCaseImpl implements SupplierCreateUseCase {

    private final SupplierService supplierService;

    @Override
    public void create(SupplierCreateRequest request) {
        SupplierEntity supplierEntity = SupplierEntity.builder()
                .name(request.name())
                .code(request.code())
                .taxId(request.taxId())
                .phone(request.phone())
                .fax(request.fax())
                .registerAddress(request.registerAddress())
                .businessAddress(request.businessAddress())
                .build();
        supplierService.create(supplierEntity);
    }

}

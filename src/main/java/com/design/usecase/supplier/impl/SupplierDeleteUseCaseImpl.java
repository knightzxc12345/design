package com.design.usecase.supplier.impl;

import com.design.entity.SupplierEntity;
import com.design.service.SupplierService;
import com.design.usecase.supplier.SupplierDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierDeleteUseCaseImpl implements SupplierDeleteUseCase {

    private final SupplierService supplierService;

    @Override
    public void delete(String uuid) {
        SupplierEntity supplierEntity = supplierService.findByUuid(uuid);
        supplierService.delete(supplierEntity);
    }

}

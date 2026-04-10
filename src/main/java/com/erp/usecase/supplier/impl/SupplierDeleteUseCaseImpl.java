package com.erp.usecase.supplier.impl;

import com.erp.service.SupplierService;
import com.erp.usecase.supplier.SupplierDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierDeleteUseCaseImpl implements SupplierDeleteUseCase {

    private final SupplierService supplierService;

    @Override
    public void delete(UUID uuid) {
        supplierService.delete(uuid);
    }

}

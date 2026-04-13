package com.erp.usecase.supplier.impl;

import com.erp.service.SupplierContractService;
import com.erp.usecase.supplier.SupplierContractDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierContractDeleteUseCaseImpl implements SupplierContractDeleteUseCase {

    private final SupplierContractService supplierContractService;

    @Override
    public void delete(UUID uuid) {
        supplierContractService.delete(uuid);
    }

}

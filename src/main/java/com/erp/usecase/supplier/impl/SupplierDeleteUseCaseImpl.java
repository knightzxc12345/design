package com.erp.usecase.supplier.impl;

import com.erp.service.SupplierContractService;
import com.erp.service.SupplierService;
import com.erp.usecase.supplier.SupplierDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierDeleteUseCaseImpl implements SupplierDeleteUseCase {

    private final SupplierService supplierService;

    private final SupplierContractService supplierContractService;

    @Override
    public void delete(UUID uuid) {
        supplierService.delete(uuid);
        // 刪除所有供應商聯絡人
        supplierContractService.deleteAll(uuid);
    }

}

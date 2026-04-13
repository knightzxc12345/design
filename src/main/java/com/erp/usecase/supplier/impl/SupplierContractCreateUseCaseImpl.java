package com.erp.usecase.supplier.impl;

import com.erp.controller.supplier.request.SupplierContractCreateRequest;
import com.erp.entity.SupplierContractEntity;
import com.erp.entity.SupplierEntity;
import com.erp.service.SupplierContractService;
import com.erp.service.SupplierService;
import com.erp.usecase.supplier.SupplierContractCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierContractCreateUseCaseImpl implements SupplierContractCreateUseCase {

    private final SupplierService supplierService;

    private final SupplierContractService supplierContractService;

    @Override
    public void create(SupplierContractCreateRequest request) {
        // 檢核供應商
        SupplierEntity supplierEntity = supplierService.findByUuid(request.supplierUuid());
        SupplierContractEntity supplierContractEntity = SupplierContractEntity.builder()
                .supplierUuid(request.supplierUuid())
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .title(request.title())
                .build();
        supplierContractService.create(supplierContractEntity);
    }

}

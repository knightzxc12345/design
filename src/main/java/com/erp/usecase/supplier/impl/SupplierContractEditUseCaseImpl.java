package com.erp.usecase.supplier.impl;

import com.erp.controller.supplier.request.SupplierContractEditRequest;
import com.erp.entity.SupplierContractEntity;
import com.erp.entity.SupplierEntity;
import com.erp.service.SupplierContractService;
import com.erp.service.SupplierService;
import com.erp.usecase.supplier.SupplierContractEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierContractEditUseCaseImpl implements SupplierContractEditUseCase {

    private final SupplierService supplierService;

    private final SupplierContractService supplierContractService;

    @Override
    public void edit(UUID uuid, SupplierContractEditRequest request) {
        // 檢核
        SupplierEntity supplierEntity = supplierService.findByUuid(request.supplierUuid());
        SupplierContractEntity supplierContractEntity = supplierContractService.findByUuid(uuid);
        supplierContractEntity = init(supplierContractEntity, request);
        supplierContractService.edit(supplierContractEntity);
    }

    private SupplierContractEntity init(SupplierContractEntity supplierContractEntity, SupplierContractEditRequest request){
        supplierContractEntity.setSupplierUuid(request.supplierUuid());
        supplierContractEntity.setName(request.name());
        supplierContractEntity.setPhone(request.phone());
        supplierContractEntity.setEmail(request.email());
        supplierContractEntity.setTitle(request.title());
        return supplierContractEntity;
    }

}

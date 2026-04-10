package com.erp.usecase.supplier.impl;

import com.erp.controller.supplier.request.SupplierEditRequest;
import com.erp.entity.SupplierEntity;
import com.erp.service.SupplierService;
import com.erp.usecase.supplier.SupplierEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierEditUseCaseImpl implements SupplierEditUseCase {

    private final SupplierService supplierService;

    @Override
    public void edit(UUID uuid, SupplierEditRequest request) {
        SupplierEntity supplierEntity = supplierService.findByUuid(uuid);
        supplierEntity = init(supplierEntity, request);
        supplierService.edit(supplierEntity);
    }

    private SupplierEntity init(SupplierEntity supplierEntity, SupplierEditRequest request){
        supplierEntity.setName(request.name());
        supplierEntity.setCode(request.code());
        supplierEntity.setTaxId(request.taxId());
        supplierEntity.setPhone(request.phone());
        supplierEntity.setFax(request.fax());
        supplierEntity.setRegisterAddress(request.registerAddress());
        supplierEntity.setBusinessAddress(request.businessAddress());
        supplierEntity.setStatus(request.status());
        return supplierEntity;
    }

}

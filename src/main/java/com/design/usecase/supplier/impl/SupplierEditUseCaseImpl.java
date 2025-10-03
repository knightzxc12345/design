package com.design.usecase.supplier.impl;

import com.design.controller.supplier.requst.SupplierEditRequest;
import com.design.entity.SupplierEntity;
import com.design.service.SupplierService;
import com.design.usecase.supplier.SupplierEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierEditUseCaseImpl implements SupplierEditUseCase {

    private final SupplierService supplierService;

    @Override
    public void edit(String uuid, SupplierEditRequest request) {
        SupplierEntity supplierEntity = supplierService.findByUuid(uuid);
        supplierEntity = init(supplierEntity, request);
        supplierService.edit(supplierEntity);
    }

    private SupplierEntity init(SupplierEntity supplierEntity, SupplierEditRequest request){
        supplierEntity.setName(request.name());
        supplierEntity.setVatNumber(request.vatNumber());
        supplierEntity.setPhone(request.phone());
        supplierEntity.setFax(request.fax());
        supplierEntity.setEmail(request.email());
        supplierEntity.setAddress(request.address());
        supplierEntity.setContactName(request.contactName());
        supplierEntity.setContactPhone(request.contactPhone());
        supplierEntity.setRemark(request.remark());
        supplierEntity.setStatus(request.status());
        return supplierEntity;
    }

}

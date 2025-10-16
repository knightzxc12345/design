package com.design.usecase.supplier.impl;

import com.design.controller.supplier.requst.SupplierCreateRequest;
import com.design.entity.SupplierEntity;
import com.design.service.SupplierService;
import com.design.usecase.supplier.SupplierCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierCreateUseCaseImpl implements SupplierCreateUseCase {

    private final SupplierService supplierService;

    @Transactional
    @Override
    public void create(SupplierCreateRequest request) {
        SupplierEntity supplierEntity = init(request);
        supplierService.create(supplierEntity);
    }

    private SupplierEntity init(SupplierCreateRequest request){
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setName(request.name());
        supplierEntity.setVatNumber(request.vatNumber());
        supplierEntity.setPhone(request.phone());
        supplierEntity.setFax(request.fax());
        supplierEntity.setEmail(request.email());
        supplierEntity.setAddress(request.address());
        supplierEntity.setContactName(request.contactName());
        supplierEntity.setContactPhone(request.contactPhone());
        supplierEntity.setRemark(request.remark());
        return supplierEntity;
    }

}

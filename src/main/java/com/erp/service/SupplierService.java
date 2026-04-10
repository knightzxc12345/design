package com.erp.service;

import com.erp.entity.SupplierEntity;
import com.erp.entity.enums.SupplierStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SupplierService {

    SupplierEntity create(SupplierEntity supplierEntity);

    SupplierEntity edit(SupplierEntity supplierEntity);

    SupplierEntity delete(UUID uuid);

    SupplierEntity findByUuid(UUID uuid);

    List<SupplierEntity> findAll(String keyword, SupplierStatus status);

    Page<SupplierEntity> findByPage(Pageable pageable, String keyword, SupplierStatus status);

}

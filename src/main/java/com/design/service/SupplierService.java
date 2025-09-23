package com.design.service;

import com.design.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SupplierService {

    void create(SupplierEntity supplierEntity);

    void edit(SupplierEntity supplierEntity);

    void delete(SupplierEntity supplierEntity);

    SupplierEntity findByUuid(String uuid);

    List<SupplierEntity> findAll(String keyword);

    Page<SupplierEntity> findByPage(String keyword, Pageable pageable);

}

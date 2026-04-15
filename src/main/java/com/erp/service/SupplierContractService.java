package com.erp.service;

import com.erp.entity.SupplierContractEntity;

import java.util.List;
import java.util.UUID;

public interface SupplierContractService {

    SupplierContractEntity create(SupplierContractEntity supplierContractEntity);

    SupplierContractEntity edit(SupplierContractEntity supplierContractEntity);

    SupplierContractEntity delete(UUID uuid);

    void deleteAll(UUID supplierUuid);

    SupplierContractEntity findByUuid(UUID uuid);

    List<SupplierContractEntity> findAllBySupplierUuid(UUID supplierUuid);

}

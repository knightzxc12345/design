package com.erp.usecase.supplier.impl;

import com.erp.controller.supplier.response.SupplierContractFindAllResponse;
import com.erp.controller.supplier.response.SupplierContractFindResponse;
import com.erp.entity.SupplierContractEntity;
import com.erp.service.SupplierContractService;
import com.erp.usecase.supplier.SupplierContractFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierContractFindUseCaseImpl implements SupplierContractFindUseCase {

    private final SupplierContractService supplierContractService;

    @Override
    public SupplierContractFindResponse findDetail(UUID uuid) {
        SupplierContractEntity supplierContractEntity = supplierContractService.findByUuid(uuid);
        return new SupplierContractFindResponse(
                supplierContractEntity.getSupplierUuid(),
                supplierContractEntity.getName(),
                supplierContractEntity.getPhone(),
                supplierContractEntity.getEmail(),
                supplierContractEntity.getTitle()
        );
    }

    @Override
    public List<SupplierContractFindAllResponse> findAll(UUID supplierUuid) {
        List<SupplierContractEntity> supplierContractEntities = supplierContractService.findAllBySupplierUuid(supplierUuid);
        return formatList(supplierContractEntities);
    }

    private List<SupplierContractFindAllResponse> formatList(List<SupplierContractEntity> supplierContractEntities){
        if(null == supplierContractEntities || supplierContractEntities.isEmpty()){
            return List.of();
        }
        return supplierContractEntities.stream()
                .map(supplierContractEntity -> new SupplierContractFindAllResponse(
                        supplierContractEntity.getSupplierUuid(),
                        supplierContractEntity.getName(),
                        supplierContractEntity.getPhone(),
                        supplierContractEntity.getEmail(),
                        supplierContractEntity.getTitle()
                ))
                .toList();
    }

}

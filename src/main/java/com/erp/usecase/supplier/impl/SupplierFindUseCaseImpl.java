package com.erp.usecase.supplier.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.supplier.request.SupplierFindRequest;
import com.erp.controller.supplier.request.SupplierPageRequest;
import com.erp.controller.supplier.response.SupplierFindAllResponse;
import com.erp.controller.supplier.response.SupplierFindResponse;
import com.erp.entity.SupplierContractEntity;
import com.erp.entity.SupplierEntity;
import com.erp.service.SupplierContractService;
import com.erp.service.SupplierService;
import com.erp.usecase.supplier.SupplierFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierFindUseCaseImpl implements SupplierFindUseCase {

    private final SupplierService supplierService;

    private final SupplierContractService supplierContractService;

    @Override
    public SupplierFindResponse findDetail(UUID uuid) {
        SupplierEntity supplierEntity = supplierService.findByUuid(uuid);
        List<SupplierContractEntity> supplierContractEntities = supplierContractService.findAll(supplierEntity.getUuid());
        return new SupplierFindResponse(
                supplierEntity.getUuid(),
                supplierEntity.getName(),
                supplierEntity.getCode(),
                supplierEntity.getTaxId(),
                supplierEntity.getPhone(),
                supplierEntity.getFax(),
                supplierEntity.getRegisterAddress(),
                supplierEntity.getBusinessAddress(),
                supplierEntity.getStatus(),
                supplierContractEntities.stream()
                        .map(supplierContractEntity -> new SupplierFindResponse.Contract(
                                supplierContractEntity.getSupplierUuid(),
                                supplierContractEntity.getName(),
                                supplierContractEntity.getPhone(),
                                supplierContractEntity.getEmail(),
                                supplierContractEntity.getTitle()
                        )).toList()
        );
    }

    @Override
    public List<SupplierFindAllResponse> findAll(SupplierFindRequest request) {
        List<SupplierEntity> supplierEntities = supplierService.findAll(request.keyword(), request.status());
        return formatList(supplierEntities);
    }

    @Override
    public PageResponse<SupplierFindAllResponse> findByPage(SupplierPageRequest request) {
        Page<SupplierEntity> supplierEntityPage = supplierService.findByPage(
                PageRequest.of(request.page(), request.size()),
                request.keyword(),
                request.status()
        );
        return formatPage(supplierEntityPage);
    }

    private List<SupplierFindAllResponse> formatList(List<SupplierEntity> supplierEntities){
        if(null == supplierEntities || supplierEntities.isEmpty()){
            return List.of();
        }
        return supplierEntities.stream()
                .map(supplierEntity -> new SupplierFindAllResponse(
                        supplierEntity.getUuid(),
                        supplierEntity.getName(),
                        supplierEntity.getCode(),
                        supplierEntity.getTaxId(),
                        supplierEntity.getPhone(),
                        supplierEntity.getFax(),
                        supplierEntity.getStatus()
                ))
                .toList();
    }

    private PageResponse<SupplierFindAllResponse> formatPage(Page<SupplierEntity> supplierEntityPage){
        List<SupplierFindAllResponse> responses = formatList(supplierEntityPage.getContent());
        return new PageResponse<SupplierFindAllResponse>(
                supplierEntityPage.getNumber(),
                supplierEntityPage.getSize(),
                supplierEntityPage.getTotalElements(),
                supplierEntityPage.getTotalPages(),
                responses
        );
    }

}

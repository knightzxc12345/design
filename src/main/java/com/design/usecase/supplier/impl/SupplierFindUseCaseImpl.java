package com.design.usecase.supplier.impl;

import com.design.controller.common.response.PageResponse;
import com.design.controller.supplier.requst.SupplierFindRequest;
import com.design.controller.supplier.requst.SupplierPageRequest;
import com.design.controller.supplier.response.SupplierFindAllResponse;
import com.design.controller.supplier.response.SupplierFindResponse;
import com.design.controller.supplier.response.SupplierPageResponse;
import com.design.entity.SupplierEntity;
import com.design.service.SupplierService;
import com.design.usecase.supplier.SupplierFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierFindUseCaseImpl implements SupplierFindUseCase {

    private final SupplierService supplierService;

    @Override
    public SupplierFindResponse findDetail(String uuid) {
        SupplierEntity supplierEntity = supplierService.findByUuid(uuid);
        return new SupplierFindResponse(
                supplierEntity.getName(),
                supplierEntity.getVatNumber(),
                supplierEntity.getPhone(),
                supplierEntity.getFax(),
                supplierEntity.getEmail(),
                supplierEntity.getAddress(),
                supplierEntity.getContactName(),
                supplierEntity.getContactPhone(),
                supplierEntity.getRemark(),
                supplierEntity.getStatus()
        );
    }

    @Override
    public List<SupplierFindAllResponse> findAll(SupplierFindRequest request) {
        List<SupplierEntity> supplierEntities = supplierService.findAll(request.keyword());
        return formatList(supplierEntities);
    }

    @Override
    public SupplierPageResponse findByPage(SupplierPageRequest request) {
        Page<SupplierEntity> supplierEntityPage = supplierService.findByPage(
                request.keyword(),
                PageRequest.of(request.page(), request.size())
        );
        return formatPage(supplierEntityPage);
    }

    private List<SupplierFindAllResponse> formatList(List<SupplierEntity> supplierEntities){
        if(supplierEntities == null || supplierEntities.isEmpty()){
            return List.of();
        }
        return supplierEntities.stream()
                .map(supplierEntity -> new SupplierFindAllResponse(
                        supplierEntity.getUuid(),
                        supplierEntity.getName(),
                        supplierEntity.getVatNumber(),
                        supplierEntity.getEmail(),
                        supplierEntity.getContactName(),
                        supplierEntity.getContactPhone(),
                        supplierEntity.getStatus()
                ))
                .toList();
    }

    private SupplierPageResponse formatPage(Page<SupplierEntity> supplierEntityPage){
        List<SupplierFindAllResponse> responses = formatList(supplierEntityPage.getContent());
        return new SupplierPageResponse(
                new PageResponse(
                        supplierEntityPage.getNumber(),
                        supplierEntityPage.getSize(),
                        supplierEntityPage.getTotalPages()
                ),
                responses
        );
    }

}

package com.erp.usecase.material.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.material.request.MaterialFindRequest;
import com.erp.controller.material.request.MaterialPageRequest;
import com.erp.controller.material.response.MaterialFindAllResponse;
import com.erp.controller.material.response.MaterialFindResponse;
import com.erp.entity.MaterialEntity;
import com.erp.service.MaterialService;
import com.erp.usecase.material.MaterialFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialFindUseCaseImpl implements MaterialFindUseCase {

    private final MaterialService materialService;

    @Override
    public MaterialFindResponse findDetail(UUID uuid) {
        MaterialEntity materialEntity = materialService.findByUuid(uuid);
        return new MaterialFindResponse(
                materialEntity.getUuid(),
                materialEntity.getSupplierUuid(),
                materialEntity.getName(),
                materialEntity.getCode(),
                materialEntity.getSpec(),
                materialEntity.getUnit(),
                materialEntity.getRemark(),
                materialEntity.getCost(),
                materialEntity.getStatus()
        );
    }

    @Override
    public List<MaterialFindAllResponse> findAll(MaterialFindRequest request) {
        List<MaterialEntity> materialEntities = materialService.findAll(
                request.supplierUuid(),
                request.keyword(),
                request.status()
        );
        return formatList(materialEntities);
    }

    @Override
    public PageResponse<MaterialFindAllResponse> findPage(MaterialPageRequest request) {
        Page<MaterialEntity> materialEntityPage = materialService.findPage(
                PageRequest.of(request.page(), request.size()),
                request.supplierUuid(),
                request.keyword(),
                request.status()
        );
        return formatPage(materialEntityPage);
    }

    private List<MaterialFindAllResponse> formatList(List<MaterialEntity> materialEntities){
        if(null == materialEntities || materialEntities.isEmpty()){
            return List.of();
        }
        return materialEntities.stream()
                .map(materialEntity -> new MaterialFindAllResponse(
                        materialEntity.getUuid(),
                        materialEntity.getSupplierUuid(),
                        materialEntity.getName(),
                        materialEntity.getCode(),
                        materialEntity.getSpec(),
                        materialEntity.getUnit(),
                        materialEntity.getCost(),
                        materialEntity.getStatus()   
                ))
                .toList();
    }

    private PageResponse<MaterialFindAllResponse> formatPage(Page<MaterialEntity> materialEntityPage){
        List<MaterialFindAllResponse> responses = formatList(materialEntityPage.getContent());
        return new PageResponse<MaterialFindAllResponse>(
                materialEntityPage.getNumber(),
                materialEntityPage.getSize(),
                materialEntityPage.getTotalElements(),
                materialEntityPage.getTotalPages(),
                responses
        );
    } 

}

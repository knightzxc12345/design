package com.erp.usecase.permission.impl;

import com.erp.controller.permission.request.PermissionCreateRequest;
import com.erp.entity.PermissionEntity;
import com.erp.service.PermissionService;
import com.erp.usecase.permission.PermissionCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionCreateUseCaseImpl implements PermissionCreateUseCase {

    private final PermissionService permissionService;

    @Override
    public void create(PermissionCreateRequest request) {
        PermissionEntity permissionEntity = init(request);
        permissionService.create(permissionEntity);
    }

    private PermissionEntity init(PermissionCreateRequest request){
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setName(request.name());
        permissionEntity.setUrl(request.url());
        permissionEntity.setParentUuid(request.parentUuid());
        permissionEntity.setSort(request.sort());
        return permissionEntity;
    }

}

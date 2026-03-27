package com.erp.usecase.permission.impl;

import com.erp.controller.permission.request.PermissionCreateRequest;
import com.erp.entity.PermissionEntity;
import com.erp.service.PermissionService;
import com.erp.usecase.permission.PermissionCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PermissionCreateUseCaseImpl implements PermissionCreateUseCase {

    private final PermissionService permissionService;

    @Transactional
    @Override
    public void create(PermissionCreateRequest request) {
        PermissionEntity permissionEntity = PermissionEntity.builder()
                .name(request.name())
                .code(request.code())
                .parentUuid(request.parentUuid())
                .sort(request.sort())
                .build();
        permissionService.create(permissionEntity);
    }

}

package com.erp.usecase.permission.impl;

import com.erp.controller.permission.request.PermissionFindRequest;
import com.erp.controller.permission.response.PermissionFindAllResponse;
import com.erp.entity.PermissionEntity;
import com.erp.entity.RoleEntity;
import com.erp.entity.RolePermissionEntity;
import com.erp.service.PermissionService;
import com.erp.service.RolePermissionService;
import com.erp.service.RoleService;
import com.erp.usecase.permission.PermissionFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionFindUseCaseImpl implements PermissionFindUseCase {

    private final PermissionService permissionService;

    private final RoleService roleService;

    private final RolePermissionService rolePermissionService;

    @Transactional(readOnly = true)
    @Override
    public List<PermissionFindAllResponse> findAll(PermissionFindRequest request) {
        RoleEntity roleEntity = roleService.findByUuid(request.roleUuid());
        List<RolePermissionEntity> rolePermissionActionEntities = rolePermissionService.findAllByRoleUuid(request.roleUuid());
        List<PermissionEntity> permissionEntities = permissionService.findAll();
        return null;
    }

}

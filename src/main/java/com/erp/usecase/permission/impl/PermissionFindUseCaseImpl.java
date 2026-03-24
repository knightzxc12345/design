package com.erp.usecase.permission.impl;

import com.erp.controller.permission.request.PermissionFindRequest;
import com.erp.controller.permission.response.PermissionFindAllResponse;
import com.erp.entity.ActionEntity;
import com.erp.entity.PermissionEntity;
import com.erp.entity.RoleEntity;
import com.erp.entity.RolePermissionActionEntity;
import com.erp.service.ActionService;
import com.erp.service.PermissionService;
import com.erp.service.RolePermissionActionService;
import com.erp.service.RoleService;
import com.erp.usecase.permission.PermissionFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionFindUseCaseImpl implements PermissionFindUseCase {

    private final PermissionService permissionService;

    private final RoleService roleService;

    private final ActionService actionService;

    private final RolePermissionActionService rolePermissionActionService;

    @Override
    public List<PermissionFindAllResponse> findAll(PermissionFindRequest request) {
        RoleEntity roleEntity = roleService.findByUuid(request.roleUuid());
        List<RolePermissionActionEntity> rolePermissionActionEntities = rolePermissionActionService.findAllByRoleUuid(null);
        List<PermissionEntity> permissionEntities = permissionService.findAll();
        List<ActionEntity> actionEntities = actionService.findAll();
        return null;
    }

}

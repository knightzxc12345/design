package com.erp.usecase.permission.impl;

import com.erp.controller.permission.request.PermissionEditRequest;
import com.erp.service.ActionService;
import com.erp.service.PermissionService;
import com.erp.service.RolePermissionActionService;
import com.erp.service.RoleService;
import com.erp.usecase.permission.PermissionEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionEditUseCaseImpl implements PermissionEditUseCase {

    private final PermissionService permissionService;

    private final RoleService roleService;

    private final ActionService actionService;

    private final RolePermissionActionService rolePermissionActionService;

    @Override
    public void bind(UUID roleUuid, PermissionEditRequest request) {

    }

}

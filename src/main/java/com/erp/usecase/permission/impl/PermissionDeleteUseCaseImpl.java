package com.erp.usecase.permission.impl;

import com.erp.entity.PermissionEntity;
import com.erp.service.PermissionService;
import com.erp.service.RolePermissionActionService;
import com.erp.usecase.permission.PermissionDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionDeleteUseCaseImpl implements PermissionDeleteUseCase {

    private final PermissionService permissionService;

    private final RolePermissionActionService rolePermissionActionService;

    @Transactional
    @Override
    public void delete(UUID uuid) {
        PermissionEntity permissionEntity = permissionService.findByUuid(uuid);
        // 刪除權限
        permissionService.delete(permissionEntity);
        // 刪除角色權限
        rolePermissionActionService.deleteAllByPermissionUuid(permissionEntity.getUuid());
    }

}

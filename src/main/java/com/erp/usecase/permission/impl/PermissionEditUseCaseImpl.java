package com.erp.usecase.permission.impl;

import com.erp.base.response.enums.PermissionCode;
import com.erp.controller.permission.request.PermissionBindRequest;
import com.erp.controller.permission.request.PermissionEditRequest;
import com.erp.entity.PermissionEntity;
import com.erp.entity.RoleEntity;
import com.erp.entity.RolePermissionEntity;
import com.erp.handler.BusinessException;
import com.erp.service.PermissionService;
import com.erp.service.RolePermissionService;
import com.erp.service.RoleService;
import com.erp.usecase.permission.PermissionEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionEditUseCaseImpl implements PermissionEditUseCase {

    private final PermissionService permissionService;

    private final RoleService roleService;

    private final RolePermissionService rolePermissionService;

    @Transactional
    @Override
    public void edit(UUID permissionUuid, PermissionEditRequest request) {
        PermissionEntity permissionEntity = permissionService.findByUuid(permissionUuid);
        permissionEntity = init(permissionEntity, request);
        permissionService.edit(permissionEntity);
    }

    @Transactional
    @Override
    public void bind(UUID roleUuid, PermissionBindRequest request) {
        // 確認角色存在
        RoleEntity roleEntity = roleService.findByUuid(roleUuid);
        // 清除舊資料
        rolePermissionService.deleteAllByRoleUuid(roleUuid);
        // 收集 permission UUID（含樹狀）
        List<UUID> permissionUuids = new ArrayList<>();
        collectPermissionUuids(request.permissions(), permissionUuids);
        // 驗證 permission 是否存在
        Map<UUID, PermissionEntity> permissionMap =
                permissionService.findAllInUuids(permissionUuids)
                        .stream()
                        .collect(Collectors.toMap(PermissionEntity::getUuid, Function.identity()));
        if (permissionMap.size() != permissionUuids.size()) {
            throw new BusinessException(PermissionCode.INVALID_EXIT);
        }
        // 建立關聯
        List<RolePermissionEntity> result = new ArrayList<>();
        buildRolePermissions(
                request.permissions(),
                roleUuid,
                permissionMap,
                result
        );
        // 批次寫入
        rolePermissionService.createAll(result);
    }

    private PermissionEntity init(PermissionEntity permissionEntity, PermissionEditRequest request){
        permissionEntity.setName(request.name());
        permissionEntity.setCode(request.code());
        permissionEntity.setSort(request.sort());
        permissionEntity.setStatus(request.status());
        return permissionEntity;
    }

    private void collectPermissionUuids(
            List<PermissionBindRequest.Permission> permissions,
            List<UUID> permissionUuids) {
        for (var p : permissions) {
            permissionUuids.add(p.permissionUuid());
            if (p.children() != null && !p.children().isEmpty()) {
                collectPermissionUuids(p.children(), permissionUuids);
            }
        }
    }

    private void buildRolePermissions(
            List<PermissionBindRequest.Permission> permissions,
            UUID roleUuid,
            Map<UUID, PermissionEntity> permissionMap,
            List<RolePermissionEntity> result) {
        for (var p : permissions) {
            PermissionEntity permissionEntity = permissionMap.get(p.permissionUuid());
            result.add(RolePermissionEntity.builder()
                    .roleUuid(roleUuid)
                    .permissionUuid(permissionEntity.getUuid())
                    .build());
            if (p.children() != null && !p.children().isEmpty()) {
                buildRolePermissions(
                        p.children(),
                        roleUuid,
                        permissionMap,
                        result
                );
            }
        }
    }

}

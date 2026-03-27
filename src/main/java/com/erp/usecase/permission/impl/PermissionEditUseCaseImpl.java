package com.erp.usecase.permission.impl;

import com.erp.base.response.enums.ActionCode;
import com.erp.base.response.enums.PermissionCode;
import com.erp.controller.permission.request.PermissionBindRequest;
import com.erp.controller.permission.request.PermissionEditRequest;
import com.erp.entity.ActionEntity;
import com.erp.entity.PermissionEntity;
import com.erp.entity.RoleEntity;
import com.erp.entity.RolePermissionActionEntity;
import com.erp.handler.BusinessException;
import com.erp.service.ActionService;
import com.erp.service.PermissionService;
import com.erp.service.RolePermissionActionService;
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

    private final ActionService actionService;

    private final RolePermissionActionService rolePermissionActionService;

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
        // 取得角色
        RoleEntity roleEntity = roleService.findByUuid(roleUuid);
        // 刪除腳色綁定權限
        rolePermissionActionService.deleteAllByRoleUuid(roleUuid);
        // 收集permission UUID
        List<UUID> permissionUuids = new ArrayList<>();
        // 收集action UUID
        List<UUID> actionUuids = new ArrayList<>();
        collectUuids(request.permissions(), permissionUuids, actionUuids);
        // 驗證權限
        Map<UUID, PermissionEntity> permissionMap =
                permissionService.findAllByUuids(permissionUuids)
                        .stream()
                        .collect(Collectors.toMap(PermissionEntity::getUuid, Function.identity()));
        if (permissionMap.size() != permissionUuids.size()) {
            throw new BusinessException(PermissionCode.INVALID_EXIT);
        }
        // 驗證動作
        Map<UUID, ActionEntity> actionMap =
                actionService.findAllByUuids(actionUuids)
                        .stream()
                        .collect(Collectors.toMap(ActionEntity::getUuid, Function.identity()));
        if (actionMap.size() != actionUuids.size()) {
            throw new BusinessException(ActionCode.INVALID_EXIT);
        }
        List<RolePermissionActionEntity> result = new ArrayList<>();
        processPermissions(
                request.permissions(),
                roleUuid,
                permissionMap,
                actionMap,
                result
        );
        rolePermissionActionService.createAll(result);
    }

    private PermissionEntity init(PermissionEntity permissionEntity, PermissionEditRequest request){
        permissionEntity.setName(request.name());
        permissionEntity.setCode(request.code());
        permissionEntity.setSort(request.sort());
        permissionEntity.setStatus(request.status());
        return permissionEntity;
    }

    private void collectUuids(
            List<PermissionBindRequest.Permission> permissions,
            List<UUID> permissionUuids,
            List<UUID> actionUuids) {
        for (var p : permissions) {
            permissionUuids.add(p.permissionUuid());
            actionUuids.addAll(p.actionUuids());
            if (p.children() != null && !p.children().isEmpty()) {
                collectUuids(p.children(), permissionUuids, actionUuids);
            }
        }
    }

    private void processPermissions(
            List<PermissionBindRequest.Permission> permissions,
            UUID roleUuid,
            Map<UUID, PermissionEntity> permissionMap,
            Map<UUID, ActionEntity> actionMap,
            List<RolePermissionActionEntity> result) {
        for (var p : permissions) {
            PermissionEntity permissionEntity = permissionMap.get(p.permissionUuid());
            for (UUID actionUuid : p.actionUuids()) {
                ActionEntity actionEntity = actionMap.get(actionUuid);
                if (!actionEntity.getPermissionUuid().equals(permissionEntity.getUuid())) {
                    throw new BusinessException(ActionCode.INVALID_EXIT);
                }
                result.add(init(roleUuid, permissionEntity, actionEntity));
            }
            if (p.children() != null && !p.children().isEmpty()) {
                processPermissions(
                        p.children(),
                        roleUuid,
                        permissionMap,
                        actionMap,
                        result
                );
            }
        }
    }

    private RolePermissionActionEntity init(UUID roleUuid, PermissionEntity permissionEntity, ActionEntity actionEntity){
        return RolePermissionActionEntity.builder()
                .roleUuid(roleUuid)
                .permissionUuid(permissionEntity.getUuid())
                .actionUuid(actionEntity.getUuid())
                .build();
    }

}

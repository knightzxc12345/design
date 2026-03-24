package com.erp.usecase.permission.impl;

import com.erp.base.response.enums.ActionCode;
import com.erp.base.response.enums.PermissionCode;
import com.erp.controller.permission.request.PermissionBindRequest;
import com.erp.controller.permission.request.PermissionEditRequest;
import com.erp.entity.ActionEntity;
import com.erp.entity.PermissionEntity;
import com.erp.entity.RoleEntity;
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
import java.util.stream.Stream;

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
        // 取出權限及動作uuid清單
        List<UUID> permissionUuids = new ArrayList<>();
        List<UUID> actionUuids = new ArrayList<>();
        Stream.concat(
                request.permissions().stream(),
                request.parentPermissions().stream()
        ).forEach(p -> {
            permissionUuids.add(p.permissionUuid());
            actionUuids.addAll(p.actionUuids());
        });
        // 組合權限清單
        Map<UUID, PermissionEntity> permissionMap =
                permissionService.findAllByUuids(permissionUuids)
                        .stream()
                        .collect(Collectors.toMap(PermissionEntity::getUuid, Function.identity()));
        // 取何動作清單
        Map<UUID, ActionEntity> actionMap =
                actionService.findAllByUuids(actionUuids)
                        .stream()
                        .collect(Collectors.toMap(ActionEntity::getUuid, Function.identity()));
        // 檢查權限清單
        if (permissionMap.size() != permissionUuids.size()) {
            throw new BusinessException(PermissionCode.INVALID_EXIT);
        }
        // 檢查動作清單
        if (actionMap.size() != actionUuids.size()) {
            throw new BusinessException(ActionCode.INVALID_EXIT);
        }
    }

    private PermissionEntity init(PermissionEntity permissionEntity, PermissionEditRequest request){
        permissionEntity.setName(request.name());
        permissionEntity.setCode(request.code());
        permissionEntity.setSort(request.sort());
        permissionEntity.setStatus(request.status());
        return permissionEntity;
    }

}

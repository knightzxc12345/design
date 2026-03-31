package com.erp.service;

import com.erp.entity.RolePermissionEntity;

import java.util.List;
import java.util.UUID;

public interface RolePermissionService {

    RolePermissionEntity create(RolePermissionEntity rolePermissionEntity);

    void createAll(List<RolePermissionEntity> rolePermissionActionEntities);

    void deleteAllByPermissionUuid(UUID permissionUuid);

    void deleteAllByRoleUuid(UUID roleUuid);

    RolePermissionEntity findByPermissionUuid(UUID roleUuid, UUID permissionUud);

    List<RolePermissionEntity> findAllByRoleUuid(UUID roleUuid);

}

package com.erp.service;

import com.erp.entity.RolePermissionEntity;

import java.util.List;
import java.util.UUID;

public interface RolePermissionService {

    void createAll(List<RolePermissionEntity> rolePermissionActionEntities);

    void deleteAllByPermissionUuid(UUID permissionUuid);

    void deleteAllByRoleUuid(UUID roleUuid);

    List<RolePermissionEntity> findAllByRoleUuid(UUID roleUuid);

}

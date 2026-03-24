package com.erp.service;

import com.erp.entity.RolePermissionActionEntity;

import java.util.List;
import java.util.UUID;

public interface RolePermissionActionService {

    void createAll(List<RolePermissionActionEntity> rolePermissionActionEntities);

    void deleteAllByPermissionUuid(UUID permissionUuid);

    void deleteAllByRoleUuid(UUID roleUuid);

    List<RolePermissionActionEntity> findAllByRoleUuid(UUID roleUuid);

}

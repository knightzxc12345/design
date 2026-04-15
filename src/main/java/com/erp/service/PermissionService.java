package com.erp.service;

import com.erp.entity.PermissionEntity;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    PermissionEntity create(PermissionEntity permissionEntity);

    PermissionEntity edit(PermissionEntity permissionEntity);

    PermissionEntity delete(UUID uuid);

    PermissionEntity findByUuid(UUID uuid);

    PermissionEntity findByCode(String code);

    List<PermissionEntity> findAll();

    List<PermissionEntity> findAllInUuids(List<UUID> uuids);

}

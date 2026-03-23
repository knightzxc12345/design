package com.erp.service;

import com.erp.entity.PermissionEntity;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    void create(PermissionEntity permissionEntity);

    void delete(PermissionEntity permissionEntity);

    PermissionEntity findByUuid(UUID uuid);

    List<PermissionEntity> findAll();

}

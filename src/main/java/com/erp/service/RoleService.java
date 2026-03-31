package com.erp.service;

import com.erp.entity.RoleEntity;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleEntity create(RoleEntity roleEntity);

    RoleEntity edit(RoleEntity roleEntity);

    RoleEntity delete(UUID uuid);

    RoleEntity findByUuid(UUID uuid);

    RoleEntity findByName(String name);

    List<RoleEntity> findAll();

}

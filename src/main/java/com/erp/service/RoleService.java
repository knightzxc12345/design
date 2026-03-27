package com.erp.service;

import com.erp.entity.RoleEntity;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    void create(RoleEntity roleEntity);

    void edit(RoleEntity roleEntity);

    void delete(UUID uuid);

    RoleEntity findByUuid(UUID uuid);

    List<RoleEntity> findAll();

}

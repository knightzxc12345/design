package com.erp.service;

import com.erp.entity.RoleEntity;
import com.erp.entity.UserEntity;
import com.erp.entity.UserRoleEntity;

import java.util.UUID;

public interface UserRoleService {

    void create(UserEntity userEntity, RoleEntity roleEntity);

    void delete(UserRoleEntity userRoleEntity);

    UserRoleEntity findByUserUuid(UUID userUuid);

}

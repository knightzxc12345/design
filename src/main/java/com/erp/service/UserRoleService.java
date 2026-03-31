package com.erp.service;

import com.erp.entity.UserRoleEntity;

import java.util.UUID;

public interface UserRoleService {

    UserRoleEntity create(UserRoleEntity userRoleEntity);

    UserRoleEntity deleteByUserUuid(UUID userUuid);

    UserRoleEntity findByUserUuid(UUID userUuid);

}

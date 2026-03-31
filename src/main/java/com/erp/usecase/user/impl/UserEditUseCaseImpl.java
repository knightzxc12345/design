package com.erp.usecase.user.impl;

import com.erp.controller.user.request.UserEditRequest;
import com.erp.entity.RoleEntity;
import com.erp.entity.UserEntity;
import com.erp.entity.UserRoleEntity;
import com.erp.service.RoleService;
import com.erp.service.UserRoleService;
import com.erp.service.UserService;
import com.erp.usecase.user.UserEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEditUseCaseImpl implements UserEditUseCase {

    private final UserService userService;

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    @Transactional
    @Override
    public void edit(UUID uuid, UserEditRequest request) {
        UserEntity userEntity = userService.findByUuid(uuid);
        RoleEntity roleEntity = roleService.findByUuid(request.roleUuid());
        userEntity = init(userEntity, request);
        // 編輯使用者
        userService.edit(userEntity);
        // 刪除使用者角色
        userRoleService.deleteByUserUuid(userEntity.getUuid());
        UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                .userUuid(userEntity.getUuid())
                .roleUuid(roleEntity.getUuid())
                .build();
        // 新增使用者角色
        userRoleService.create(userRoleEntity);
    }

    private UserEntity init(UserEntity userEntity, UserEditRequest request){
        userEntity.setAccount(request.account());
        userEntity.setName(request.name());
        userEntity.setEmail(request.email());
        userEntity.setMobile(request.mobile());
        return userEntity;
    }

}

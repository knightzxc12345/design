package com.erp.service.impl;

import com.erp.entity.RoleEntity;
import com.erp.entity.UserEntity;
import com.erp.entity.UserRoleEntity;
import com.erp.service.InitUserService;
import com.erp.service.RoleService;
import com.erp.service.UserRoleService;
import com.erp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InitUserServiceImpl implements InitUserService {

    private final UserService userService;

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    @Override
    public void init() {
        // ===== 1. 取得管理員角色 =====
        RoleEntity roleEntity = roleService.findByName("管理者");
        // ===== 2. 初始化使用者 =====
        UserEntity userEntity = initUser(
                UserEntity.builder()
                        .account("admin")
                        .password("Aa000000")
                        .email("admin@gmail.com")
                        .name("管理者")
                        .build()
        );
        UserRoleEntity userRoleEntity = initUserRole(
                UserRoleEntity.builder()
                        .userUuid(userEntity.getUuid())
                        .roleUuid(roleEntity.getUuid())
                        .build()
        );
    }

    // =============================
    // 🔥 初始化使用者
    // =============================
    private UserEntity initUser(UserEntity userEntity) {
        UserEntity isExists = userService.findByAccount(userEntity.getAccount());
        if (isExists != null) {
            return isExists;
        }
        return userService.create(userEntity);
    }

    // =============================
    // 🔥 初始化使用者角色
    // =============================
    private UserRoleEntity initUserRole(UserRoleEntity userRoleEntity){
        UserRoleEntity isExists = userRoleService.findByUserUuidInit(userRoleEntity.getUserUuid());
        if (isExists != null) {
            return isExists;
        }
        return userRoleService.create(userRoleEntity);
    }

}

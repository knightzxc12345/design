package com.erp.service.impl;

import com.erp.entity.PermissionEntity;
import com.erp.entity.RoleEntity;
import com.erp.entity.RolePermissionEntity;
import com.erp.service.InitRoleService;
import com.erp.service.PermissionService;
import com.erp.service.RolePermissionService;
import com.erp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InitRoleServiceImpl implements InitRoleService {

    private final RoleService roleService;

    private final RolePermissionService rolePermissionService;

    private final PermissionService permissionService;

    @Override
    public void init() {

        // ===== 1. 初始化角色 =====
        RoleEntity adminRole = initAdminRole(
                RoleEntity.builder()
                        .name("管理者")
                        .build()
        );

        // ===== 2. 初始化模組：權限管理 =====
        initModule(
                adminRole,
                "權限管理",
                "PERMISSION",
                List.of(
                        action("CREATE", "新增", 1),
                        action("EDIT", "編輯", 2),
                        action("DELETE", "刪除", 3),
                        action("READ", "查詢", 4),
                        action("BIND", "綁定", 5)
                ),
                1
        );
    }

    // =============================
    // 🔥 模組初始化（核心）
    // =============================
    private void initModule(RoleEntity role, String moduleName, String moduleCode, List<Action> actions, int sort) {
        // 建立 parent permission
        PermissionEntity parent = initPermission(
                PermissionEntity.builder()
                        .name(moduleName)
                        .code(moduleCode)
                        .parentUuid(null)
                        .sort(sort)
                        .build()
        );

        // 綁定 parent
        initRolePermission(role.getUuid(), parent.getUuid());

        // 建立 action permissions
        for (Action action : actions) {
            PermissionEntity permission = initPermission(
                    PermissionEntity.builder()
                            .name(moduleName + "_" + action.name)
                            .code(moduleCode + ":" + action.code)
                            .parentUuid(parent.getUuid())
                            .sort(action.sort)
                            .build()
            );
            // 綁定 role
            initRolePermission(role.getUuid(), permission.getUuid());
        }
    }

    // =============================
    // 🔥 Action DTO（內部用）
    // =============================
    private Action action(String code, String name, int sort) {
        return new Action(code, name, sort);
    }

    private record Action(String code, String name, int sort) {}

    // =============================
    // 🔥 初始化角色
    // =============================
    private RoleEntity initAdminRole(RoleEntity roleEntity) {
        RoleEntity isExists = roleService.findByName(roleEntity.getName());
        if (isExists != null) {
            return isExists;
        }
        return roleService.create(roleEntity);
    }

    // =============================
    // 🔥 初始化權限
    // =============================
    private PermissionEntity initPermission(PermissionEntity permissionEntity) {
        PermissionEntity isExists = permissionService.findByCode(permissionEntity.getCode());
        if (isExists != null) {
            return isExists;
        }
        return permissionService.create(permissionEntity);
    }

    // =============================
    // 🔥 初始化角色權限
    // =============================
    private RolePermissionEntity initRolePermission(UUID roleUuid, UUID permissionUuid) {
        RolePermissionEntity isExists =
                rolePermissionService.findByPermissionUuid(roleUuid, permissionUuid);
        if (isExists != null) {
            return isExists;
        }
        RolePermissionEntity entity = RolePermissionEntity.builder()
                .roleUuid(roleUuid)
                .permissionUuid(permissionUuid)
                .build();
        return rolePermissionService.create(entity);
    }

}
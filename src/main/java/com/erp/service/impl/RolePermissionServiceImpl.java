package com.erp.service.impl;

import com.erp.entity.RolePermissionEntity;
import com.erp.repository.RolePermissionRepository;
import com.erp.service.RolePermissionService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public void createAll(List<RolePermissionEntity> rolePermissionActionEntities) {
        if(null == rolePermissionActionEntities || rolePermissionActionEntities.isEmpty()){
            return;
        }
        for(RolePermissionEntity rolePermissionEntity : rolePermissionActionEntities){
            rolePermissionEntity.setIsDeleted(false);
            rolePermissionEntity.setCreateTime(Instant.now());
            rolePermissionEntity.setCreateUser(UserUtil.getUserUuid());
        }
        rolePermissionRepository.saveAll(rolePermissionActionEntities);
    }

    @Override
    public void deleteAllByPermissionUuid(UUID permissionUuid) {
        List<RolePermissionEntity> rolePermissionActionEntities = rolePermissionRepository.findByIsDeletedFalseAndPermissionUuid(permissionUuid);
        if(null == rolePermissionActionEntities || rolePermissionActionEntities.isEmpty()){
            return;
        }
        for(RolePermissionEntity rolePermissionEntity : rolePermissionActionEntities){
            rolePermissionEntity.setIsDeleted(true);
            rolePermissionEntity.setDeletedTime(Instant.now());
            rolePermissionEntity.setDeletedUser(UserUtil.getUserUuid());
        }
        rolePermissionRepository.saveAll(rolePermissionActionEntities);
    }

    @Override
    public void deleteAllByRoleUuid(UUID roleUuid) {
        List<RolePermissionEntity> rolePermissionActionEntities = rolePermissionRepository.findByIsDeletedFalseAndRoleUuid(roleUuid);
        if(null == rolePermissionActionEntities || rolePermissionActionEntities.isEmpty()){
            return;
        }
        for(RolePermissionEntity rolePermissionEntity : rolePermissionActionEntities){
            rolePermissionEntity.setIsDeleted(true);
            rolePermissionEntity.setDeletedTime(Instant.now());
            rolePermissionEntity.setDeletedUser(UserUtil.getUserUuid());
        }
        rolePermissionRepository.saveAll(rolePermissionActionEntities);
    }

    @Override
    public List<RolePermissionEntity> findAllByRoleUuid(UUID roleUuid) {
        if(null == roleUuid){
            return rolePermissionRepository.findByIsDeletedFalse();
        }
        return rolePermissionRepository.findByIsDeletedFalseAndRoleUuid(roleUuid);
    }

}

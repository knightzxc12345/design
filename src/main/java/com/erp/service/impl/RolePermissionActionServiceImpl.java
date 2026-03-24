package com.erp.service.impl;

import com.erp.entity.RolePermissionActionEntity;
import com.erp.repository.RolePermissionActionRepository;
import com.erp.service.RolePermissionActionService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RolePermissionActionServiceImpl implements RolePermissionActionService {

    private final RolePermissionActionRepository rolePermissionActionRepository;

    @Override
    public void createAll(List<RolePermissionActionEntity> rolePermissionActionEntities) {
        if(null == rolePermissionActionEntities || rolePermissionActionEntities.isEmpty()){
            return;
        }
        for(RolePermissionActionEntity rolePermissionActionEntity : rolePermissionActionEntities){
            rolePermissionActionEntity.setIsDeleted(false);
            rolePermissionActionEntity.setCreateTime(Instant.now());
            rolePermissionActionEntity.setCreateUser(UserUtil.getUserUuid());
        }
        rolePermissionActionRepository.saveAll(rolePermissionActionEntities);
    }

    @Override
    public void deleteAllByPermissionUuid(UUID permissionUuid) {
        List<RolePermissionActionEntity> rolePermissionActionEntities = rolePermissionActionRepository.findByIsDeletedFalseAndPermissionUuid(permissionUuid);
        if(null == rolePermissionActionEntities || rolePermissionActionEntities.isEmpty()){
            return;
        }
        for(RolePermissionActionEntity rolePermissionActionEntity : rolePermissionActionEntities){
            rolePermissionActionEntity.setIsDeleted(true);
            rolePermissionActionEntity.setDeletedTime(Instant.now());
            rolePermissionActionEntity.setDeletedUser(UserUtil.getUserUuid());
        }
        rolePermissionActionRepository.saveAll(rolePermissionActionEntities);
    }

    @Override
    public void deleteAllByRoleUuid(UUID roleUuid) {
        List<RolePermissionActionEntity> rolePermissionActionEntities = rolePermissionActionRepository.findByIsDeletedFalseAndRoleUuid(roleUuid);
        if(null == rolePermissionActionEntities || rolePermissionActionEntities.isEmpty()){
            return;
        }
        for(RolePermissionActionEntity rolePermissionActionEntity : rolePermissionActionEntities){
            rolePermissionActionEntity.setIsDeleted(true);
            rolePermissionActionEntity.setDeletedTime(Instant.now());
            rolePermissionActionEntity.setDeletedUser(UserUtil.getUserUuid());
        }
        rolePermissionActionRepository.saveAll(rolePermissionActionEntities);
    }

    @Override
    public List<RolePermissionActionEntity> findAllByRoleUuid(UUID roleUuid) {
        if(null == roleUuid){
            return rolePermissionActionRepository.findByIsDeletedFalse();
        }
        return rolePermissionActionRepository.findByIsDeletedFalseAndRoleUuid(roleUuid);
    }

}

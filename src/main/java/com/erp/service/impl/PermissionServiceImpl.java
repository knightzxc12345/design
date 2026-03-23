package com.erp.service.impl;

import com.erp.base.response.enums.PermissionCode;
import com.erp.base.response.enums.RoleCode;
import com.erp.base.response.enums.UserCode;
import com.erp.entity.PermissionEntity;
import com.erp.handler.BusinessException;
import com.erp.repository.PermissionRepository;
import com.erp.service.PermissionService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public void create(PermissionEntity permissionEntity) {
        permissionRepository.findByIsDeletedFalseAndName(
                permissionEntity.getName()
        ).ifPresent(r -> {
            throw new BusinessException(PermissionCode.DUPLICATE_NAME);
        });
        permissionEntity.setIsDeleted(false);
        permissionEntity.setCreateTime(Instant.now());
        permissionEntity.setCreateUser(UserUtil.getUserUuid());
        permissionRepository.save(permissionEntity);
    }

    @Override
    public void delete(PermissionEntity permissionEntity) {
        permissionEntity.setIsDeleted(true);
        permissionEntity.setDeletedTime(Instant.now());
        permissionEntity.setDeletedUser(UserUtil.getUserUuid());
        permissionRepository.save(permissionEntity);
    }

    @Override
    public PermissionEntity findByUuid(UUID uuid) {
        return permissionRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(PermissionCode.NOT_EXISTS));
    }

    @Override
    public List<PermissionEntity> findAll() {
        return permissionRepository.findByIsDeletedFalse();
    }

}

package com.erp.service.impl;

import com.erp.base.response.enums.UserRoleCode;
import com.erp.entity.RoleEntity;
import com.erp.entity.UserEntity;
import com.erp.entity.UserRoleEntity;
import com.erp.handler.BusinessException;
import com.erp.repository.UserRoleRepository;
import com.erp.service.UserRoleService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public void create(UserEntity userEntity, RoleEntity roleEntity) {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserUuid(userEntity.getUuid());
        userRoleEntity.setRoleUuid(roleEntity.getUuid());
        userRoleEntity.setIsDeleted(false);
        userRoleEntity.setCreateTime(Instant.now());
        userRoleEntity.setCreateUser(UserUtil.getUserUuid());
        userRoleRepository.save(userRoleEntity);
    }

    @Override
    public void deleteByUserUuid(UUID userUuid) {
        UserRoleEntity userRoleEntity = findByUserUuid(userUuid);
        userRoleEntity.setIsDeleted(true);
        userRoleEntity.setDeletedTime(Instant.now());
        userRoleEntity.setDeletedUser(UserUtil.getUserUuid());
        userRoleRepository.save(userRoleEntity);
    }

    @Override
    public UserRoleEntity findByUserUuid(UUID userUuid) {
        return userRoleRepository.findByIsDeletedFalseAndUserUuid(userUuid)
                .orElseThrow(() -> new BusinessException(UserRoleCode.NOT_EXISTS));
    }

}

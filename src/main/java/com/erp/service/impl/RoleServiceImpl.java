package com.erp.service.impl;

import com.erp.base.response.enums.RoleCode;
import com.erp.entity.RoleEntity;
import com.erp.entity.enums.RoleStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.RoleRepository;
import com.erp.service.RoleService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void create(RoleEntity roleEntity) {
        roleRepository.findByIsDeletedFalseAndName(
                roleEntity.getName()
        ).ifPresent(r -> {
            throw new BusinessException(RoleCode.DUPLICATE_NAME);
        });
        roleEntity.setStatus(RoleStatus.ENABLE);
        roleEntity.setIsDeleted(false);
        roleEntity.setDeletedTime(Instant.now());
        roleEntity.setDeletedUser(UserUtil.getUserUuid());
        roleRepository.save(roleEntity);
    }

    @Override
    public void edit(RoleEntity roleEntity) {
        roleRepository.findByIsDeletedFalseAndName(
                roleEntity.getName()
        ).ifPresent(r -> {
            if(!r.getUuid().equals(roleEntity.getUuid())){
                throw new BusinessException(RoleCode.DUPLICATE_NAME);
            }
        });
        roleEntity.setModifiedTime(Instant.now());
        roleEntity.setModifiedUser(UserUtil.getUserUuid());
        roleRepository.save(roleEntity);
    }

    @Override
    public void delete(RoleEntity roleEntity) {
        roleEntity.setIsDeleted(true);
        roleEntity.setDeletedTime(Instant.now());
        roleEntity.setDeletedUser(UserUtil.getUserUuid());
        roleRepository.save(roleEntity);
    }

    @Override
    public RoleEntity findByUuid(UUID uuid) {
        return roleRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(RoleCode.NOT_EXISTS));
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findByIsDeletedFalse();
    }

}

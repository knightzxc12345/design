package com.erp.usecase.role.impl;

import com.erp.entity.RoleEntity;
import com.erp.service.RoleService;
import com.erp.usecase.role.RoleDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleDeleteUseCaseImpl implements RoleDeleteUseCase {

    private final RoleService roleService;

    @Override
    public void delete(UUID uuid) {
        RoleEntity roleEntity = roleService.findByUuid(uuid);
        roleService.delete(roleEntity);
    }

}

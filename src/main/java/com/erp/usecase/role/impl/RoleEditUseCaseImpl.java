package com.erp.usecase.role.impl;

import com.erp.controller.role.request.RoleEditRequest;
import com.erp.entity.RoleEntity;
import com.erp.service.RoleService;
import com.erp.usecase.role.RoleEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleEditUseCaseImpl implements RoleEditUseCase {

    private final RoleService roleService;

    @Transactional
    @Override
    public void edit(UUID uuid, RoleEditRequest request) {
        RoleEntity roleEntity = roleService.findByUuid(uuid);
        roleEntity = init(roleEntity, request);
        roleService.edit(roleEntity);
    }

    private RoleEntity init(RoleEntity roleEntity, RoleEditRequest request){
        roleEntity.setName(request.name());
        roleEntity.setStatus(request.status());
        return roleEntity;
    }

}

package com.erp.usecase.role.impl;

import com.erp.controller.role.request.RoleCreateRequest;
import com.erp.entity.RoleEntity;
import com.erp.service.RoleService;
import com.erp.usecase.role.RoleCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleCreateUseCaseImpl implements RoleCreateUseCase {

    private final RoleService roleService;

    @Transactional
    @Override
    public void create(RoleCreateRequest request) {
        RoleEntity roleEntity = init(request);
        roleService.create(roleEntity);
    }

    private RoleEntity init(RoleCreateRequest request){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(request.name());
        return roleEntity;
    }

}

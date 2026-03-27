package com.erp.usecase.role.impl;

import com.erp.service.RoleService;
import com.erp.usecase.role.RoleDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleDeleteUseCaseImpl implements RoleDeleteUseCase {

    private final RoleService roleService;

    @Transactional
    @Override
    public void delete(UUID uuid) {
        roleService.delete(uuid);
    }

}

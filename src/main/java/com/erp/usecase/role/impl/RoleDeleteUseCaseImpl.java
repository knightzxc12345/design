package com.erp.usecase.role.impl;

import com.erp.service.RoleService;
import com.erp.usecase.role.RoleDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleDeleteUseCaseImpl implements RoleDeleteUseCase {

    private final RoleService roleService;

}

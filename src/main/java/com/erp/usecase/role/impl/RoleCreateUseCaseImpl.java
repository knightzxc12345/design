package com.erp.usecase.role.impl;

import com.erp.service.RoleService;
import com.erp.usecase.role.RoleCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleCreateUseCaseImpl implements RoleCreateUseCase {

    private final RoleService roleService;

}

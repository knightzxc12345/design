package com.erp.usecase.role.impl;

import com.erp.service.RoleService;
import com.erp.usecase.role.RoleFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleFindUseCaseImpl implements RoleFindUseCase {

    private final RoleService roleService;

}

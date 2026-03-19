package com.erp.usecase.role.impl;

import com.erp.service.RoleService;
import com.erp.usecase.role.RoleEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleEditUseCaseImpl implements RoleEditUseCase {

    private final RoleService roleService;

}

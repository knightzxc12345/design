package com.erp.service.impl;

import com.erp.repository.RolePermissionActionRepository;
import com.erp.service.RolePermissionActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionActionServiceImpl implements RolePermissionActionService {

    private final RolePermissionActionRepository rolePermissionActionRepository;

}

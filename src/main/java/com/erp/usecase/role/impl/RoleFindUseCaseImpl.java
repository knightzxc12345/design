package com.erp.usecase.role.impl;

import com.erp.controller.role.request.RoleFindRequest;
import com.erp.controller.role.response.RoleFindAllResponse;
import com.erp.controller.role.response.RoleFindResponse;
import com.erp.controller.user.response.UserFindAllResponse;
import com.erp.entity.RoleEntity;
import com.erp.entity.UserEntity;
import com.erp.service.RoleService;
import com.erp.usecase.role.RoleEditUseCase;
import com.erp.usecase.role.RoleFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleFindUseCaseImpl implements RoleFindUseCase {

    private final RoleService roleService;

    @Transactional(readOnly = true)
    @Override
    public RoleFindResponse findDetail(UUID uuid) {
        RoleEntity roleEntity = roleService.findByUuid(uuid);
        return new RoleFindResponse(
                roleEntity.getName(),
                roleEntity.getStatus()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleFindAllResponse> findAll(RoleFindRequest request) {
        List<RoleEntity> roleEntities = roleService.findAll();
        return formatList(roleEntities);
    }

    private List<RoleFindAllResponse> formatList(List<RoleEntity> roleEntities){
        if(roleEntities == null || roleEntities.isEmpty()){
            return List.of();
        }
        return roleEntities.stream()
                .map(roleEntity -> new RoleFindAllResponse(
                        roleEntity.getUuid(),
                        roleEntity.getName(),
                        roleEntity.getStatus()
                ))
                .toList();
    }

}

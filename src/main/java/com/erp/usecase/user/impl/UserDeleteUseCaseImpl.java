package com.erp.usecase.user.impl;

import com.erp.entity.UserEntity;
import com.erp.entity.UserRoleEntity;
import com.erp.service.UserRoleService;
import com.erp.service.UserService;
import com.erp.usecase.user.UserDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDeleteUseCaseImpl implements UserDeleteUseCase {

    private final UserService userService;

    private final UserRoleService userRoleService;

    @Transactional
    @Override
    public void delete(UUID uuid) {
        UserEntity userEntity = userService.findByUuid(uuid);
        UserRoleEntity userRoleEntity = userRoleService.findByUserUuid(userEntity.getUuid());
        // 刪除使用者
        userService.delete(userEntity);
        // 刪除使用者角色
        userRoleService.delete(userRoleEntity);
    }

}

package com.erp.usecase.user.impl;

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
        // 刪除使用者
        userService.delete(uuid);
        // 刪除使用者角色
        userRoleService.deleteByUserUuid(uuid);
    }

}

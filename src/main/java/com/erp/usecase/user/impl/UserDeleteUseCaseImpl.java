package com.erp.usecase.user.impl;

import com.erp.entity.UserEntity;
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

    @Transactional
    @Override
    public void delete(UUID uuid) {
        UserEntity userEntity = userService.findByUuid(uuid);
        userService.delete(userEntity);
    }

}

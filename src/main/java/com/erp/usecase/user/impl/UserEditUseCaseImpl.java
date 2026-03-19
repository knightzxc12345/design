package com.erp.usecase.user.impl;

import com.erp.controller.user.request.UserEditRequest;
import com.erp.entity.UserEntity;
import com.erp.service.UserService;
import com.erp.usecase.user.UserEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEditUseCaseImpl implements UserEditUseCase {

    private final UserService userService;

    @Transactional
    @Override
    public void edit(UUID uuid, UserEditRequest request) {
        UserEntity userEntity = userService.findByUuid(uuid);
        userEntity = init(userEntity, request);
        userService.edit(userEntity);
    }

    private UserEntity init(UserEntity userEntity, UserEditRequest request){
        userEntity.setAccount(request.account());
        userEntity.setName(request.name());
        userEntity.setEmail(request.email());
        userEntity.setMobile(request.mobile());
        return userEntity;
    }

}

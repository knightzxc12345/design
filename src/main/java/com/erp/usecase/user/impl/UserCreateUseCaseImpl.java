package com.erp.usecase.user.impl;

import com.erp.controller.user.request.UserCreateRequest;
import com.erp.entity.CustomerEntity;
import com.erp.entity.UserEntity;
import com.erp.service.UserService;
import com.erp.usecase.user.UserCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCreateUseCaseImpl implements UserCreateUseCase {

    private final UserService userService;

    @Transactional
    @Override
    public void create(UserCreateRequest request) {
        UserEntity userEntity = init(request);
        userService.create(userEntity);
    }

    private UserEntity init(UserCreateRequest request){
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(request.account());
        userEntity.setPassword(request.password());
        userEntity.setName(request.name());
        userEntity.setEmail(request.email());
        userEntity.setMobile(request.mobile());
        return userEntity;
    }

}

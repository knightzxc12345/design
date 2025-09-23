package com.design.service;

import com.design.entity.UserEntity;

public interface UserService {

    void create(UserEntity userEntity);

    UserEntity findByUserName(String userName);

}

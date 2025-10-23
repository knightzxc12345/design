package com.design.service;

import com.design.entity.UserEntity;

import java.util.List;

public interface UserService {

    void create(UserEntity userEntity);

    UserEntity findByUserName(String userName);

    List<UserEntity> findAll();

}

package com.design.service.impl;

import com.design.entity.UserEntity;
import com.design.service.InitService;
import com.design.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitServiceImpl implements InitService {

    @Autowired
    private UserService userService;

    @Override
    public void init() {
        createUser("Tim", "Aa000000");
    }

    private void createUser(String userName, String password){
        UserEntity isExists = userService.findByUserName(userName);
        if(null != isExists){
            return;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userName);
        userEntity.setPassword(password);
        userService.create(userEntity);
    }

}

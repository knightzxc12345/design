package com.design.service.impl;

import com.design.base.common.Common;
import com.design.entity.UserEntity;
import com.design.repository.UserRepository;
import com.design.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void create(UserEntity userEntity) {
        userEntity.setCreateTime(Instant.now());
        userEntity.setCreateUser(Common.SYSTEM_UUID);
        userEntity.setIsDeleted(false);
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUsernameAndIsDeletedFalse(userName).orElse(null);
    }

}

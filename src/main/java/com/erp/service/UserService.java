package com.erp.service;

import com.erp.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserEntity create(UserEntity userEntity);

    UserEntity edit(UserEntity userEntity);

    UserEntity delete(UUID uuid);

    UserEntity login(String account);

    UserEntity findByUuid(UUID uuid);

    List<UserEntity> findAll(String keyword);

    Page<UserEntity> findByPage(Pageable pageable, String keyword);

}

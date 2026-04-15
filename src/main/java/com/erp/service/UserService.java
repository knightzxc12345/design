package com.erp.service;

import com.erp.entity.UserEntity;
import com.erp.entity.enums.UserStatus;
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

    UserEntity findByAccount(String account);

    List<UserEntity> findAll(
            String keyword,
            UserStatus status
    );

    Page<UserEntity> findPage(
            Pageable pageable,
            String keyword,
            UserStatus status
    );

}

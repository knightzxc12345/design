package com.erp.service.impl;

import com.erp.base.response.enums.UserCode;
import com.erp.entity.UserEntity;
import com.erp.entity.enums.UserStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.UserRepository;
import com.erp.service.UserService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void create(UserEntity userEntity) {
        // 檢查帳號
        userRepository.findByIsDeletedFalseAndUuidAndAccount(
                userEntity.getAccount()
        ).ifPresent(u -> {
            throw new BusinessException(UserCode.DUPLICATE_ACCOUNT);
        });
        // 檢查姓名
        userRepository.findByIsDeletedFalseAndUuidAndName(
                userEntity.getName()
        ).ifPresent(u -> {
            throw new BusinessException(UserCode.DUPLICATE_NAME);
        });
        // 檢查信箱
        userRepository.findByIsDeletedFalseAndUuidAndEmail(
                userEntity.getName()
        ).ifPresent(u -> {
            throw new BusinessException(UserCode.DUPLICATE_EMAIL);
        });
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setStatus(UserStatus.ENABLE);
        userEntity.setTokenVersion(1);
        userEntity.setIsDeleted(false);
        userEntity.setCreateTime(Instant.now());
        userEntity.setCreateUser(UserUtil.getUserUuid());
        userRepository.save(userEntity);
    }

    @Override
    public void edit(UserEntity userEntity) {
        // 檢查帳號
        userRepository.findByIsDeletedFalseAndUuidAndAccount(
                userEntity.getAccount()
        ).ifPresent(u -> {
            if(!u.getUuid().equals(userEntity.getUuid())){
                throw new BusinessException(UserCode.DUPLICATE_ACCOUNT);
            }
        });
        // 檢查姓名
        userRepository.findByIsDeletedFalseAndUuidAndName(
                userEntity.getName()
        ).ifPresent(u -> {
            if(!u.getUuid().equals(userEntity.getUuid())){
                throw new BusinessException(UserCode.DUPLICATE_NAME);
            }
        });
        // 檢查信箱
        userRepository.findByIsDeletedFalseAndUuidAndEmail(
                userEntity.getEmail()
        ).ifPresent(u -> {
            if(!u.getUuid().equals(userEntity.getUuid())){
                throw new BusinessException(UserCode.DUPLICATE_EMAIL);
            }
        });
        userEntity.setModifiedTime(Instant.now());
        userEntity.setModifiedUser(UserUtil.getUserUuid());
        userRepository.save(userEntity);
    }

    @Override
    public void delete(UUID uuid) {
        UserEntity userEntity = findByUuid(uuid);
        userEntity.setModifiedTime(Instant.now());
        userEntity.setModifiedUser(UserUtil.getUserUuid());
        userEntity.setIsDeleted(true);
        userEntity.setDeletedTime(Instant.now());
        userEntity.setDeletedUser(UserUtil.getUserUuid());
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity login(String account) {
        return userRepository.findByIsDeletedFalseAndUuidAndAccount(account)
                .orElseThrow(() -> new BusinessException(UserCode.NOT_EXISTS));
    }

    @Override
    public UserEntity findByUuid(UUID uuid) {
        return userRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(UserCode.NOT_EXISTS));
    }

    @Override
    public List<UserEntity> findAll(String keyword) {
        return userRepository.findAll(
                keyword
        );
    }

    @Override
    public Page<UserEntity> findByPage(Pageable pageable, String keyword) {
        return userRepository.findByPage(
                pageable,
                keyword
        );
    }

}

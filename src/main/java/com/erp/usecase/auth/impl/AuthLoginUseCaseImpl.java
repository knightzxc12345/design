package com.erp.usecase.auth.impl;

import com.erp.base.common.Common;
import com.erp.base.response.enums.UserCode;
import com.erp.controller.auth.request.LoginRequest;
import com.erp.controller.auth.response.LoginResponse;
import com.erp.entity.PermissionEntity;
import com.erp.entity.RolePermissionEntity;
import com.erp.entity.UserEntity;
import com.erp.entity.UserRoleEntity;
import com.erp.handler.BusinessException;
import com.erp.service.*;
import com.erp.usecase.auth.AuthLoginUseCase;
import com.erp.utils.HttpUtil;
import com.erp.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthLoginUseCaseImpl implements AuthLoginUseCase {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRoleService userRoleService;

    private final RedisService redisService;

    private final PermissionService permissionService;

    private final RolePermissionService rolePermissionService;

    @Override
    public LoginResponse login(LoginRequest request) {
        UserEntity userEntity = userService.login(request.account());
        if(!bCryptPasswordEncoder.matches(request.password(), userEntity.getPassword())){
            throw new BusinessException(UserCode.LOGIN_FAIL);
        }
        UserRoleEntity userRoleEntity = userRoleService.findByUserUuid(userEntity.getUuid());
        List<RolePermissionEntity> rolePermissionEntities = rolePermissionService.findAllByRoleUuid(userRoleEntity.getRoleUuid());
        List<UUID> permissions = rolePermissionEntities.stream()
                .map(RolePermissionEntity::getPermissionUuid)
                .toList();
        List<PermissionEntity> permissionEntities = permissionService.findAllByUuids(permissions);
        String redisPermissionKey = String.format("%s:%s", Common.REDIS_PERMISSION_KEY, userEntity.getUuid());
        Set<String> permissionCodes = permissionEntities.stream()
                .map(PermissionEntity::getCode)
                .collect(Collectors.toSet());
        // 放入redis
        redisService.saveAll(redisPermissionKey, permissionCodes, 7, TimeUnit.DAYS);
        // 產生 JWT
        Map<String, Object> accessClaims = new HashMap<>();
        accessClaims.put(Common.CLAIM_USER, userEntity.getUuid());
        accessClaims.put(Common.CLAIM_VERSION, userEntity.getTokenVersion());
        accessClaims.put(Common.CLAIM_TYPE, Common.CLAIM_TYPE_ACCESS);
        Map<String, Object> refreshClaims = new HashMap<>();
        refreshClaims.put(Common.CLAIM_USER, userEntity.getUuid());
        refreshClaims.put(Common.CLAIM_VERSION, userEntity.getTokenVersion());
        refreshClaims.put(Common.CLAIM_TYPE, Common.CLAIM_TYPE_REFRESH);
        String accessToken = JwtUtil.generateAccessToken(accessClaims, userEntity.getUuid());
        String refreshToken = JwtUtil.generateRefreshToken(refreshClaims, userEntity.getUuid());
        String redisRefreshKey = String.format("%s:%s", Common.REDIS_REFRESH_KEY, userEntity.getUuid().toString());
        String redisRefreshValue = String.format("%s:%s", refreshToken, userEntity.getTokenVersion());
        // 放入redis
        redisService.save(redisRefreshKey, redisRefreshValue, 7, TimeUnit.DAYS);
        // 放入cookie
        HttpUtil.addRefreshToken(refreshToken);
        return new LoginResponse(
                userEntity.getUuid(),
                userEntity.getName(),
                accessToken
        );
    }

}

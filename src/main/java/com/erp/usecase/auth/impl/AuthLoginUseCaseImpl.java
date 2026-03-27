package com.erp.usecase.auth.impl;

import com.erp.base.common.Common;
import com.erp.base.response.enums.UserCode;
import com.erp.controller.index.request.LoginRequest;
import com.erp.controller.index.response.LoginResponse;
import com.erp.entity.UserEntity;
import com.erp.handler.BusinessException;
import com.erp.security.CustomUserDetailsService;
import com.erp.service.*;
import com.erp.usecase.auth.AuthLoginUseCase;
import com.erp.utils.HttpUtil;
import com.erp.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthLoginUseCaseImpl implements AuthLoginUseCase {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RedisService redisService;

    private final UserRoleService userRoleService;

    private final RoleService roleService;

    private final RolePermissionService rolePermissionService;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public LoginResponse login(LoginRequest request) {
        UserEntity userEntity = userService.login(request.account());
        if(!bCryptPasswordEncoder.matches(request.password(), userEntity.getPassword())){
            throw new BusinessException(UserCode.LOGIN_FAIL);
        }
        // 產生 JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put(Common.CLAIM_USER, userEntity.getUuid());
        claims.put(Common.CLAIM_VERSION, userEntity.getTokenVersion());
        String accessToken = JwtUtil.generateAccessToken(claims, userEntity.getUuid());
        String refreshToken = JwtUtil.generateRefreshToken(claims, userEntity.getUuid());
        // 放入redis
        redisService.saveRefreshToken(userEntity.getUuid(), refreshToken);
        // 放入cookie
        HttpUtil.addRefreshToken(refreshToken);
        return new LoginResponse(
                userEntity.getUuid(),
                userEntity.getName(),
                accessToken
        );
    }

}

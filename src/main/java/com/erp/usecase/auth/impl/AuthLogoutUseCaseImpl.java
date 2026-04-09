package com.erp.usecase.auth.impl;

import com.erp.base.common.Common;
import com.erp.service.RedisService;
import com.erp.usecase.auth.AuthLogoutUseCase;
import com.erp.utils.HttpUtil;
import com.erp.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthLogoutUseCaseImpl implements AuthLogoutUseCase {

    private final RedisService redisService;

    @Override
    public void logout() {
        String token = HttpUtil.getRefreshToken();
        // 解析 token
        Claims claims = JwtUtil.extractAllClaims(token);
        Date expiration = claims.getExpiration();
        long ttl = expiration.getTime() - System.currentTimeMillis();
    }

}

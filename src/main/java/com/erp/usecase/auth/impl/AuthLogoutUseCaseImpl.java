package com.erp.usecase.auth.impl;

import com.erp.base.common.Common;
import com.erp.service.RedisService;
import com.erp.usecase.auth.AuthLogoutUseCase;
import com.erp.utils.HttpUtil;
import com.erp.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthLogoutUseCaseImpl implements AuthLogoutUseCase {

    private final RedisService redisService;

    @Override
    public void logout() {
        String token = HttpUtil.getRefreshToken();
        String userName = JwtUtil.extractUsername(token);
        String redisRefreshKey = String.format("%s:%s", Common.REDIS_REFRESH_KEY, userName);
        redisService.delete(redisRefreshKey);
    }

}

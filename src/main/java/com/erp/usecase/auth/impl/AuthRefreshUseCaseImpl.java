package com.erp.usecase.auth.impl;

import com.erp.base.common.Common;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.auth.response.LoginRefreshResponse;
import com.erp.handler.BusinessException;
import com.erp.service.RedisService;
import com.erp.usecase.auth.AuthRefreshUseCase;
import com.erp.utils.HttpUtil;
import com.erp.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthRefreshUseCaseImpl implements AuthRefreshUseCase {

    private final RedisService redisService;

    @Override
    public LoginRefreshResponse refresh() {
        String refreshToken = HttpUtil.getRefreshToken();
        if (refreshToken == null) {
            throw new BusinessException(SystemCode.TOKEN_UNAUTHORIZED);
        }
        // 驗證token
        if(!JwtUtil.validateToken(refreshToken)){
            throw new BusinessException(SystemCode.JWT_TOKEN_EXPIRED);
        }
        Map<String, Object> claims = JwtUtil.extractAllClaims(refreshToken);
        String type = (String) claims.get(Common.CLAIM_TYPE);
        // 驗證型態
        if (!Common.CLAIM_TYPE_REFRESH.equals(type)) {
            throw new BusinessException(SystemCode.TOKEN_UNAUTHORIZED);
        }
        UUID userUuid = UUID.fromString((String) claims.get(Common.CLAIM_USER));
        Integer tokenVersion = (Integer) claims.get(Common.CLAIM_VERSION);
        // Redis 驗證
        String redisRefreshKey = String.format("%s:%s", Common.REDIS_REFRESH_KEY, userUuid);
        String redisRefreshValue = redisService.get(redisRefreshKey, String.class);
        if (redisRefreshValue == null) {
            throw new BusinessException(SystemCode.TOKEN_UNAUTHORIZED);
        }
        String[] parts = redisRefreshValue.split(":");
        String storedRefreshToken = parts[0];
        Integer storedVersion = Integer.parseInt(parts[1]);
        // 檢查token及version
        if (!storedRefreshToken.equals(refreshToken) || !storedVersion.equals(tokenVersion)) {
            throw new BusinessException(SystemCode.TOKEN_UNAUTHORIZED);
        }
        // 產生新的 access token
        Map<String, Object> accessClaims = new HashMap<>();
        accessClaims.put(Common.CLAIM_USER, userUuid);
        accessClaims.put(Common.CLAIM_VERSION, tokenVersion);
        accessClaims.put(Common.CLAIM_TYPE, Common.CLAIM_TYPE_ACCESS);
        String newAccessToken = JwtUtil.generateAccessToken(accessClaims, userUuid);
        return new LoginRefreshResponse(newAccessToken);
    }

}

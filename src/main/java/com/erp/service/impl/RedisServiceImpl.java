package com.erp.service.impl;

import com.erp.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedissonClient redissonClient;

    @Override
    public void saveRefreshToken(UUID userUuid, String refreshToken) {
        redissonClient.getBucket("refresh:" + userUuid)
                .set(refreshToken);
    }

}

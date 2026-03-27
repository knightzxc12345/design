package com.erp.service;

import java.util.UUID;

public interface RedisService {

    void saveRefreshToken(UUID userUuid, String refreshToken);

}

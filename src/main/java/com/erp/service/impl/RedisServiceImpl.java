package com.erp.service.impl;

import com.erp.base.response.enums.RedisCode;
import com.erp.handler.BusinessException;
import com.erp.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedissonClient redissonClient;

    private final ObjectMapper objectMapper;

    @Override
    public <T> void save(String key, T t, long expireTime, TimeUnit timeUnit) {
        try {
            String json = objectMapper.writeValueAsString(t);
            RBucket<String> bucket = redissonClient.getBucket(key);
            bucket.set(json, expireTime, timeUnit);
        } catch (Exception ex) {
            throw new BusinessException(RedisCode.REDIS_SAVE_ERROR);
        }
    }

    @Override
    public <T> void saveAll(String key, Set<T> set, long expireTime, TimeUnit timeUnit) {
        try {
            RSet<T> rSet = redissonClient.getSet(key);
            rSet.clear();
            rSet.addAll(set);
            rSet.expire(expireTime, timeUnit);
        } catch (Exception ex) {
            throw new BusinessException(RedisCode.REDIS_SAVE_ERROR);
        }
    }

    @Override
    public <T> void delete(String key) {
        try {
            redissonClient.getBucket(key).delete();
        }catch (Exception ex){
            throw new BusinessException(RedisCode.REDIS_DELETE_ERROR);
        }
    }

    @Override
    public <T> T get(String key, Type type) {
        try {
            RBucket<String> bucket = redissonClient.getBucket(key);
            String json = bucket.get();
            if (json == null) {
                return null;
            }
            return objectMapper.readValue(json, objectMapper.constructType(type));
        } catch (Exception e) {
            throw new BusinessException(RedisCode.REDIS_GET_ERROR);
        }
    }

    @Override
    public <T> Set<T> getAll(String key, Type type) {
        try {
            RSet<String> rSet = redissonClient.getSet(key);
            Set<String> jsonSet = rSet.readAll();
            if (jsonSet == null || jsonSet.isEmpty()) {
                return Set.of();
            }
            if (type instanceof Class<?> clazz && clazz == String.class) {
                return (Set<T>) jsonSet;
            }
            Set<T> result = new java.util.HashSet<>();
            for (String json : jsonSet) {
                T obj = objectMapper.readValue(json, objectMapper.constructType(type));
                result.add(obj);
            }
            return result;
        } catch (Exception e) {
            throw new BusinessException(RedisCode.REDIS_GET_ERROR);
        }
    }

}

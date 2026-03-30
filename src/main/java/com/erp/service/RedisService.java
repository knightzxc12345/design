package com.erp.service;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    <T> void save(String key, T t, long expireTime, TimeUnit timeUnit);

    <T> void saveAll(String key, Set<T> set, long expireTime, TimeUnit timeUnit);

    <T> void delete(String key);

    <T> T get(String key, Type type);

    <T> Set<T> getAll(String key, Type type);

}

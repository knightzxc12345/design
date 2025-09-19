package com.design.utils;

import com.design.handler.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisFrontendUtil {

    private static RedissonClient client;

    public static void init(String host, String port){
        String address = String.format("redis://%s:%s", host, port);
        Config config = new Config();
        config.useSingleServer().setAddress(address).setDatabase(2);
        client = Redisson.create(config);
    }

    public static <T> void set(String tag, String key, T t, long expireTime, TimeUnit timeUnit){
        if(StringUtils.isBlank(tag) || StringUtils.isBlank(key) || null == t){
            return;
        }
        RMap<String, String> rMap = client.getMap(tag);
        try{
            String json = JsonUtil.set(t);
            rMap.put(key, json);
            rMap.expire(expireTime, timeUnit);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(new BusinessException(SystemEnum.S00003));
        }
    }

    public static <T> void set(String tag, UUID key, T t, long expireTime, TimeUnit timeUnit){
        if(StringUtils.isBlank(tag) || null == key || null == t){
            return;
        }
        set(tag, key.toString(), t, expireTime, timeUnit);
    }

    public static void del(String tag, String key){
        if(StringUtils.isBlank(tag) || StringUtils.isBlank(key)){
            return;
        }
        RMap<String, String> rMap = client.getMap(tag);
        rMap.remove(key);
    }

    public static void del(String tag, UUID key){
        if(StringUtils.isBlank(tag) || null == key){
            return;
        }
        del(tag, key.toString());
    }

    public static void del(String tag){
        if(StringUtils.isBlank(tag)){
            return;
        }
        RMap<String, String> rMap = client.getMap(tag);
        for (RMap.Entry<String, String> entry : rMap.entrySet()) {
            rMap.remove(entry.getKey());
        }
    }

    public static void del(String tag, List<String> keys){
        if(StringUtils.isBlank(tag)){
            return;
        }
        if(null == keys || keys.isEmpty()){
            return;
        }
        RMap<String, String> rMap = client.getMap(tag);
        for (String key : keys) {
            rMap.remove(key);
        }
    }

    public static void close() {
        if (client != null) {
            client.shutdown();
        }
    }

}
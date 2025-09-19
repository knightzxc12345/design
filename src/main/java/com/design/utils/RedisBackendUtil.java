package com.design.utils;

import com.design.handler.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisBackendUtil {

    private static RedissonClient client;

    public static void init(String host, String port){
        String address = String.format("redis://%s:%s", host, port);
        Config config = new Config();
        config.useSingleServer().setAddress(address).setDatabase(1);
        client = Redisson.create(config);
    }

    public static <T> T get(String tag, String key, Type type) {
        if (StringUtils.isBlank(tag) || StringUtils.isBlank(key)) {
            return null;
        }
        RMap<String, String> rmap = client.getMap(tag);
        String json = rmap.get(key);
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JsonUtil.get(json, type);
    }

    public static <T> T get(String tag, UUID key, Type type){
        return get(tag, key.toString(), type);
    }

    public static <T> List<T> getAll(String tag, Type type){
        if(StringUtils.isBlank(tag)){
            return null;
        }
        RMap<String, String> rMap = client.getMap(tag);
        if(null == rMap || rMap.isEmpty()){
            return null;
        }
        List<T> list = new ArrayList<>();
        String json;
        T t;
        try{
            for (RMap.Entry<String, String> entry : rMap.entrySet()) {
                json = rMap.get(entry.getKey());
                t = JsonUtil.get(json, type);
                list.add(t);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(new BusinessException(SystemEnum.S00002));
        }
        return list;
    }

    public static <T> void set(String tag, String key, T t, long expireTime, TimeUnit timeUnit){
        if(StringUtils.isBlank(tag) || StringUtils.isBlank(key) || null == t){
            return;
        }
        RMap<String, String> rMap = client.getMap(tag);
        String json = JsonUtil.set(t);
        rMap.put(key, json);
        rMap.expire(expireTime, timeUnit);
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
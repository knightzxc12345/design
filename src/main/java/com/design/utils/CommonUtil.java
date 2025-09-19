package com.design.utils;

import com.design.base.common.Common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

public class CommonUtil {

    // 因角色變動，故需將所有被賦予此角色的使用者，刪除，強制重新登入
    public static void updateRole(UUID roleUuid){
        List<Auth> auths = RedisBackendUtil.getAll(Common.REDIS_AUTH, Auth.class);
        if(null == auths || auths.isEmpty()){
            return;
        }
        List<String> deletes = new ArrayList<>();
        Auth.Role role;
        for (Auth auth : auths) {
            role = auth.getRole();
            if(null == role){
                continue;
            }
            if(!roleUuid.equals(auth.getRole().getUuid())){
                continue;
            }
            deletes.add(auth.getUsername());
        }
        RedisBackendUtil.del(Common.REDIS_AUTH, deletes);
    }

    // 移除重複的值
    public static <T> List<T> removeDuplicates(List<T> list){
        if(null == list || list.isEmpty()){
            return list;
        }
        final LinkedHashSet<T> set = new LinkedHashSet<>(list);
        return new ArrayList<>(set);
    }

    // 轉換千分位符號
    public static String convert(BigDecimal bigDecimal){
        if(null == bigDecimal){
            return "";
        }
        return Common.DECIMAL_FORMAT.format(bigDecimal.intValue());
    }

    // 透過uuid取得物件
    public static <T extends BaseEntity> T getEntityByUuid(List<T> list, UUID uuid){
        if(null == list || list.isEmpty()){
            return null;
        }
        for(T t : list){
            if(t.getUuid().equals(uuid)){
                return t;
            }
        }
        return null;
    }

    // 取得uuid清單
    public static <T extends BaseEntity> List<UUID> getEntityUuids(List<T> list){
        List<UUID> uuids = new ArrayList<>();
        if(null == list || list.isEmpty()){
            return uuids;
        }
        for(T t : list){
            uuids.add(t.getUuid());
        }
        return uuids;
    }

}
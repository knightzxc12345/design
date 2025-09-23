package com.design.utils;

import com.design.base.api.CustomResponse;
import com.design.base.api.UtilCode;
import com.design.handler.BusinessException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NativeUtil {

    private static ObjectMapper mapper;

    public static <T> List<T> Convert(List<Map<String, Object>> list, Class<T> clz){
        try{
            mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            List<T> resultList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                resultList.add(mapper.convertValue(map, clz));
            }
            return resultList;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException(new CustomResponse<>(UtilCode.NATIVE_ERROR));
        }
    }

}
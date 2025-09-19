package com.design.utils;

import com.design.handler.BusinessException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {

    public static <T> T get(String json, Type type) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            JsonNode node = mapper.readTree(json);
            JavaType javaType = mapper.getTypeFactory().constructType(type);
            if (node.isArray()) {
                TypeFactory typeFactory = mapper.getTypeFactory();
                javaType = typeFactory.constructCollectionType(List.class, javaType);
                return mapper.readValue(json, javaType);
            }
            return mapper.readValue(json, javaType);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(new BusinessException(SystemEnum.S00002));
        }
    }

    public static <T> String set(T t) {
        try{
            if(null == t){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(t);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(new BusinessException(SystemEnum.S00003));
        }
    }

}
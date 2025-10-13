package com.design.utils;

import com.design.base.api.CustomResponse;
import com.design.base.api.UtilCode;
import com.design.handler.BusinessException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T get(String json, TypeReference<T> typeRef) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, typeRef);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(new CustomResponse<>(UtilCode.JSON_ERROR));
        }
    }

    public static <T> String set(T t) {
        try {
            if (t == null) return null;
            return mapper.writeValueAsString(t);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException(new CustomResponse<>(UtilCode.JSON_ERROR));
        }
    }

}
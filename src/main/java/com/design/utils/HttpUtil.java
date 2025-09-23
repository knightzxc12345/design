package com.design.utils;

import com.design.base.api.CodeMessage;
import com.design.base.api.SystemCode;
import com.design.base.common.Common;
import com.design.handler.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpUtil {

    public static HttpServletRequest getRequest(){
        return getAttribute().getRequest();
    }

    public static HttpServletResponse getResponse(){
        return getAttribute().getResponse();
    }

    public static <T extends CodeMessage> void write(T t){
        try{
            HttpServletResponse response = getResponse();
            StringBuilder sb = new StringBuilder();
            sb.append("{\"code\":\"");
            sb.append(t.getCode());
            sb.append("\",\"message\":\"");
            sb.append(t.getMessage());
            sb.append("\"}");
            response.setCharacterEncoding(Common.ENCODING);
            response.setContentType(Common.CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(sb.toString());
            response.getWriter().flush();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new BusinessException(SystemCode.SYSTEM_ERROR);
        }
    }

    private static ServletRequestAttributes getAttribute(){
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

}
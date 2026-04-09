package com.erp.utils;

import com.erp.base.common.Common;
import com.erp.base.response.enums.Code;
import com.erp.base.response.enums.SystemCode;
import com.erp.handler.BusinessException;
import jakarta.servlet.http.Cookie;
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

    public static <T extends Code> void write(T t){
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

    public static void addRefreshToken(String refreshToken) {
        HttpServletResponse response = getResponse();
        Cookie cookie = new Cookie(Common.COOKIE_REFRESH_TOKEN_KEY, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(Common.COOKIE_REFRESH_TOKEN_AGE);
        response.addCookie(cookie);
        response.addHeader("Set-Cookie",
                String.format(
                        "refreshToken=%s; Path=/; Max-Age=%d; HttpOnly; Secure; SameSite=Strict",
                        refreshToken,
                        Common.COOKIE_REFRESH_TOKEN_AGE
                )
        );
    }

    public static String getRefreshToken() {
        HttpServletRequest request = getRequest();
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if (Common.COOKIE_REFRESH_TOKEN_KEY.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void clearRefreshToken() {
        HttpServletResponse response = getResponse();
        Cookie cookie = new Cookie(Common.COOKIE_REFRESH_TOKEN_KEY, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private static ServletRequestAttributes getAttribute(){
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }

}

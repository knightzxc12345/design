package com.erp.utils;

import com.erp.base.common.Common;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil implements InitializingBean {

    @Value("${jwt.secret}")
    private String secret;

    private static Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public static String extractUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    // 取得使用者名稱
    public static String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // 取得所有Claim
    public static Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 取得指定Claim參數
    public static <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 產生AccessToken
    public static String generateAccessToken(Map<String, Object> extraClaims, UUID UserUuid) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(UserUuid.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Common.JWT_ACCESS_TOKEN_VALIDITY))
                .signWith(key)
                .compact();
    }

    // 產生RefreshToken
    public static String generateRefreshToken(Map<String, Object> extraClaims, UUID UserUuid) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(UserUuid.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Common.JWT_REFRESH_TOKEN_VALIDITY))
                .signWith(key)
                .compact();
    }

    // 驗證Token是否過期
    public static boolean validateToken(String token){
        return extractExpiration(token).before(new Date());
    }

    // 取得角色
    public static String getRoleFromToken(String token) {
        Claims claims = extractAllClaims(token);
        Object role = claims.get(Common.CLAIM_ROLE);
        if (role instanceof String) {
            return (String) role;
        }
        return null;
    }

    // 取得權限
    public static List<String> getPermissionsFromToken(String token) {
        Claims claims = extractAllClaims(token);
        Object permissions = claims.get(Common.CLAIM_PERMISSION);
        if (permissions instanceof List) {
            return ((List<?>) permissions).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // 取得Token過期時間
    private static Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

}
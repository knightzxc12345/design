package com.erp.filter;

import com.erp.base.common.Common;
import com.erp.base.response.enums.SystemCode;
import com.erp.service.RedisService;
import com.erp.utils.HttpUtil;
import com.erp.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RedisService redisService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(Common.TOKEN_HEADER);
        if(StringUtils.isBlank(header)){
            filterChain.doFilter(request, response);
            return;
        }
        if(header == null && !header.startsWith(Common.TOKEN_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        // 驗證token型態
        Claims claims = JwtUtil.extractAllClaims(token);
        String type = claims.get(Common.CLAIM_TYPE, String.class);
        if (!Common.CLAIM_TYPE_ACCESS.equals(type)) {
            HttpUtil.write(SystemCode.TOKEN_INVALID_TYPE);
            return;
        }
        // 取得userName
        String userName = JwtUtil.extractUsername(token);
        if(StringUtils.isBlank(userName)){
            HttpUtil.write(SystemCode.JWT_USER_NOT_FOUND);
            return;
        }
        // 驗證token
        if(!JwtUtil.validateToken(token)){
            HttpUtil.write(SystemCode.JWT_TOKEN_EXPIRED);
            return;
        }
        // 取得UserDetail
        if(null != userName && null == SecurityContextHolder.getContext().getAuthentication()){
            String redisKey = String.format("%s:%s", Common.REDIS_PERMISSION_KEY, userName);
            Set<String> permissions = redisService.getAll(redisKey, String.class);
            List<SimpleGrantedAuthority> authorities = permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userName, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

}

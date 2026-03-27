package com.erp.filter;

import com.erp.base.common.Common;
import com.erp.base.response.enums.SystemCode;
import com.erp.utils.HttpUtil;
import com.erp.utils.JwtUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(Common.TOKEN_HEADER);
        if(StringUtils.isBlank(token)){
            filterChain.doFilter(request, response);
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
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 放入角色
            String role = JwtUtil.getRoleFromToken(token);
            if(StringUtils.isNotBlank(role)){
                authorities.add(new SimpleGrantedAuthority(role));
            }
            // 放入權限
            List<String> permissions = JwtUtil.getPermissionsFromToken(token);
            if(null != permissions && !permissions.isEmpty()){
                for (String permission : permissions) {
                    authorities.add(new SimpleGrantedAuthority(permission));
                }
            }
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userName, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

}

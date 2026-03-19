package com.erp.aop;

import com.erp.aop.annotation.Permission;
import com.erp.base.common.Common;
import com.erp.base.response.enums.SystemCode;
import com.erp.handler.BusinessException;
import com.erp.utils.JwtUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Aspect
@Component
public class PermissionAspect {

    @Pointcut("@annotation(permission)")
    public void permissionPointcut(Permission permission) {

    }

    @Around("permissionPointcut(permission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(Common.TOKEN_HEADER);
        if (StringUtils.isBlank(token) || !token.startsWith(Common.TOKEN_PREFIX)) {
            throw new BusinessException(SystemCode.TOKEN_UNDEFINED);
        }
        token = token.substring(7);
        List<String> permissions = JwtUtil.getPermissionsFromToken(token);
        if (!permissions.contains(permission.value())) {
            throw new BusinessException(SystemCode.PERMISSION_DENIED);
        }
        return joinPoint.proceed();
    }

}

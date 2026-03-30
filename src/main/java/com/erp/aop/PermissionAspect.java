package com.erp.aop;

import com.erp.aop.annotation.Permission;
import com.erp.base.common.Common;
import com.erp.base.response.enums.SystemCode;
import com.erp.handler.BusinessException;
import com.erp.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final RedisService redisService;

    @Pointcut("@annotation(permission)")
    public void permissionPointcut(Permission permission) {

    }

    @Around("permissionPointcut(permission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) auth.getPrincipal();
        String redisKey = String.format("%s:%s", Common.REDIS_PERMISSION_KEY, userName);
        Set<String> permissions = redisService.getAll(redisKey, String.class);
        if (permissions == null || !permissions.contains(permission.value())) {
            throw new BusinessException(SystemCode.PERMISSION_DENIED);
        }
        return joinPoint.proceed();
    }

}

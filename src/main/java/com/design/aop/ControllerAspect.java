package com.design.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(* com.xk.upms.controller..*(..))")
    public void pointcut() { }

    @Before("pointcut()")
    public void logRequest(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String clazzName = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info(
                "[{}] - [{}] Request URI -> {}",
                clazzName,
                methodName,
                request.getRequestURI()
        );
        log.info(
                "[{}] - [{}] Request Method -> {}",
                clazzName,
                methodName,
                request.getMethod()
        );
        log.info(
                "[{}] - [{}] Request -> {}",
                clazzName,
                methodName,
                requestToString(joinPoint)
        );
    }

    @AfterReturning(returning = "obj", pointcut = "pointcut()")
    public void doAfterReturning(JoinPoint joinPoint, Object obj) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String clazzName = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        if(obj instanceof AbstractCollection){
            int size = ((AbstractCollection) obj).size();
            log.info(
                    "[{}] - [{}] Response Size -> {}",
                    clazzName,
                    methodName,
                    size
            );
        }
        log.info(
                "[{}] - [{}] Response -> {}",
                clazzName,
                methodName,
                obj
        );
    }

    protected String requestToString(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        if(codeSignature.getParameterNames().length == 0){
            return "";
        }else{
            StringBuffer sb = new StringBuffer("[");
            String[] names = codeSignature.getParameterNames();
            Object[] args = joinPoint.getArgs();
            for(int i = 0; i < names.length; i++ ){
                sb.append("" +  names[i] + "->" + args[i]);
                if(i != names.length -1 ){
                    sb.append(", ");
                }else{
                    sb.append("]");
                }
            }
            return sb.toString();
        }
    }

}
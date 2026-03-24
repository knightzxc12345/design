package com.erp.utils;

import com.erp.service.impl.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class UserUtil {

    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        return null;
    }

    public static UUID getUserUuid() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getUuid() : null;
    }

}

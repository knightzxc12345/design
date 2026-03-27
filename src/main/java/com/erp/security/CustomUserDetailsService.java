package com.erp.security;

import com.erp.base.response.enums.SystemCode;
import com.erp.entity.UserEntity;
import com.erp.handler.BusinessException;
import com.erp.repository.UserRepository;
import com.erp.service.impl.CustomUserDetails;
import com.erp.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByIsDeletedFalseAndUuidAndName(username)
                .orElseThrow(() -> new BusinessException(SystemCode.LOGIN_FAIL));
        return new CustomUserDetails(userEntity);
    }

}

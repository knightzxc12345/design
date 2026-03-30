package com.erp.security;

import com.erp.base.response.enums.SystemCode;
import com.erp.entity.UserEntity;
import com.erp.handler.BusinessException;
import com.erp.repository.UserRepository;
import com.erp.service.impl.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByIsDeletedFalseAndUuidAndName(username)
                .orElseThrow(() -> new BusinessException(SystemCode.LOGIN_FAIL));
        return new CustomUserDetails(userEntity);
    }

}

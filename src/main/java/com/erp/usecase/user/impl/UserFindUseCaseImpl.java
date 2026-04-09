package com.erp.usecase.user.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.user.request.UserFindRequest;
import com.erp.controller.user.request.UserPageRequest;
import com.erp.controller.user.response.UserFindAllResponse;
import com.erp.controller.user.response.UserFindResponse;
import com.erp.entity.UserEntity;
import com.erp.service.UserService;
import com.erp.usecase.user.UserFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFindUseCaseImpl implements UserFindUseCase {

    private final UserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserFindResponse findDetail(UUID uuid) {
        UserEntity userEntity = userService.findByUuid(uuid);
        return new UserFindResponse(
                userEntity.getUuid(),
                userEntity.getAccount(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getMobile(),
                userEntity.getStatus()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserFindAllResponse> findAll(UserFindRequest request) {
        List<UserEntity> userEntities = userService.findAll(request.keyword());
        return formatList(userEntities);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse findByPage(UserPageRequest request) {
        Page<UserEntity> userEntityPage = userService.findByPage(
                PageRequest.of(request.page(), request.size()),
                request.keyword()
        );
        return formatPage(userEntityPage);
    }

    private List<UserFindAllResponse> formatList(List<UserEntity> userEntities){
        if(userEntities == null || userEntities.isEmpty()){
            return List.of();
        }
        return userEntities.stream()
                .map(userEntity -> new UserFindAllResponse(
                        userEntity.getUuid(),
                        userEntity.getAccount(),
                        userEntity.getName(),
                        userEntity.getEmail(),
                        userEntity.getMobile(),
                        userEntity.getStatus()
                ))
                .toList();
    }

    private PageResponse formatPage(Page<UserEntity> userEntityPage){
        List<UserFindAllResponse> responses = formatList(userEntityPage.getContent());
        return new PageResponse(
                userEntityPage.getNumber(),
                userEntityPage.getSize(),
                userEntityPage.getTotalElements(),
                userEntityPage.getTotalPages(),
                responses
        );
    }

}

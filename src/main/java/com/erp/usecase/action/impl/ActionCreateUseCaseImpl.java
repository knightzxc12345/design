package com.erp.usecase.action.impl;

import com.erp.controller.action.request.ActionCreateRequest;
import com.erp.entity.ActionEntity;
import com.erp.service.ActionService;
import com.erp.usecase.action.ActionCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActionCreateUseCaseImpl implements ActionCreateUseCase {

    private final ActionService actionService;

    @Transactional
    @Override
    public void create(ActionCreateRequest request) {
        ActionEntity actionEntity = ActionEntity.builder()
                .permissionUuid(request.permiossionUuid())
                .name(request.name())
                .method(request.method())
                .sort(request.sort())
                .build();
        actionService.create(actionEntity);
    }

}

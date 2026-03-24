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
        ActionEntity actionEntity = init(request);
        actionService.create(actionEntity);
    }

    private ActionEntity init(ActionCreateRequest request){
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setPermissionUuid(request.permiossionUuid());
        actionEntity.setName(request.name());
        actionEntity.setMethod(request.method());
        actionEntity.setSort(request.sort());
        return actionEntity;
    }

}

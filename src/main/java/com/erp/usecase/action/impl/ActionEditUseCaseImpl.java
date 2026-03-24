package com.erp.usecase.action.impl;

import com.erp.controller.action.request.ActionEditRequest;
import com.erp.entity.ActionEntity;
import com.erp.service.ActionService;
import com.erp.usecase.action.ActionEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionEditUseCaseImpl implements ActionEditUseCase {

    private final ActionService actionService;

    @Transactional
    @Override
    public void edit(UUID uuid, ActionEditRequest request) {
        ActionEntity actionEntity = actionService.findByUuid(uuid);
        actionEntity = init(actionEntity, request);
        actionService.edit(actionEntity);
    }

    private ActionEntity init(ActionEntity actionEntity, ActionEditRequest request){
        actionEntity.setPermissionUuid(request.permiossionUuid());
        actionEntity.setName(request.name());
        actionEntity.setMethod(request.method());
        actionEntity.setSort(request.sort());
        actionEntity.setStatus(request.status());
        return actionEntity;
    }

}

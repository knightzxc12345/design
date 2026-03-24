package com.erp.usecase.action.impl;

import com.erp.controller.action.request.ActionFindRequest;
import com.erp.controller.action.response.ActionFindAllResponse;
import com.erp.controller.action.response.ActionFindResponse;
import com.erp.entity.ActionEntity;
import com.erp.service.ActionService;
import com.erp.usecase.action.ActionFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionFindUseCaseImpl implements ActionFindUseCase {

    private final ActionService actionService;

    @Transactional(readOnly = true)
    @Override
    public ActionFindResponse findDetail(UUID uuid) {
        ActionEntity actionEntity = actionService.findByUuid(uuid);
        return new ActionFindResponse(
                actionEntity.getUuid(),
                actionEntity.getPermissionUuid(),
                actionEntity.getName(),
                actionEntity.getMethod(),
                actionEntity.getSort(),
                actionEntity.getStatus()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ActionFindAllResponse> findAll(ActionFindRequest request) {
        List<ActionEntity> actionEntities = actionService.findAllByPermissionUuid(request.permissionUuid());
        return format(actionEntities);
    }

    private List<ActionFindAllResponse> format(List<ActionEntity> actionEntities){
        return actionEntities.stream()
                .map(entity -> new ActionFindAllResponse(
                        entity.getUuid(),
                        entity.getPermissionUuid(),
                        entity.getName(),
                        entity.getMethod(),
                        entity.getSort(),
                        entity.getStatus()
                ))
                .toList();
    }

}

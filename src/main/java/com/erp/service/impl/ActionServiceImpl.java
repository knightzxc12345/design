package com.erp.service.impl;

import com.erp.base.response.enums.ActionCode;
import com.erp.entity.ActionEntity;
import com.erp.entity.enums.ActionStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.ActionRepository;
import com.erp.service.ActionService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Override
    public void create(ActionEntity actionEntity) {
        actionEntity.setStatus(ActionStatus.ENABLE);
        actionEntity.setIsDeleted(false);
        actionEntity.setCreateTime(Instant.now());
        actionEntity.setCreateUser(UserUtil.getUserUuid());
        actionRepository.save(actionEntity);
    }

    @Override
    public void edit(ActionEntity actionEntity) {
        actionEntity.setModifiedTime(Instant.now());
        actionEntity.setModifiedUser(UserUtil.getUserUuid());
        actionRepository.save(actionEntity);
    }

    @Override
    public void delete(ActionEntity actionEntity) {
        actionEntity.setIsDeleted(false);
        actionEntity.setDeletedTime(Instant.now());
        actionEntity.setDeletedUser(UserUtil.getUserUuid());
        actionRepository.save(actionEntity);
    }

    @Override
    public ActionEntity findByUuid(UUID uuid) {
        return actionRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(ActionCode.NOT_EXISTS));
    }

    @Override
    public List<ActionEntity> findAll() {
        return actionRepository.findAll();
    }

    @Override
    public List<ActionEntity> findAllByPermissionUuid(UUID permissionUuid) {
        if(null == permissionUuid){
            return actionRepository.findByIsDeletedFalse();
        }
        return actionRepository.findByIsDeletedFalseAndPermissionUuid(permissionUuid);
    }

    @Override
    public List<ActionEntity> findAllByUuids(List<UUID> uuids) {
        return actionRepository.findByUuidIn(uuids);
    }

}

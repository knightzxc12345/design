package com.erp.service.impl;

import com.erp.entity.ActionEntity;
import com.erp.repository.ActionRepository;
import com.erp.service.ActionService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Override
    public void create(ActionEntity actionEntity) {
        actionEntity.setCreateTime(Instant.now());
        actionEntity.setCreateUser(UserUtil.getUserUuid());
        actionRepository.save(actionEntity);
    }

    @Override
    public List<ActionEntity> findAll() {
        return actionRepository.findAll();
    }

}

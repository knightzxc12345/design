package com.erp.usecase.action.impl;

import com.erp.service.ActionService;
import com.erp.usecase.action.ActionDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionDeleteUseCaseImpl implements ActionDeleteUseCase {

    private final ActionService actionService;

    @Transactional
    @Override
    public void delete(UUID uuid) {
        actionService.delete(uuid);
    }

}

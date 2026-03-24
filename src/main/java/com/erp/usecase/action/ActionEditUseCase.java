package com.erp.usecase.action;

import com.erp.controller.action.request.ActionEditRequest;

import java.util.UUID;

public interface ActionEditUseCase {

    void edit(UUID uuid, ActionEditRequest request);

}

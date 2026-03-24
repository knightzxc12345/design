package com.erp.usecase.action;

import com.erp.controller.action.request.ActionFindRequest;
import com.erp.controller.action.response.ActionFindAllResponse;
import com.erp.controller.action.response.ActionFindResponse;

import java.util.List;
import java.util.UUID;

public interface ActionFindUseCase {

    ActionFindResponse findDetail(UUID uuid);

    List<ActionFindAllResponse> findAll(ActionFindRequest request);

}

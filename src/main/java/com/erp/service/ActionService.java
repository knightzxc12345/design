package com.erp.service;

import com.erp.entity.ActionEntity;

import java.util.List;

public interface ActionService {

    void create(ActionEntity actionEntity);

    List<ActionEntity> findAll();

}

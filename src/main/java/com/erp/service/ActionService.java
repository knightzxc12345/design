package com.erp.service;

import com.erp.entity.ActionEntity;

import java.util.List;
import java.util.UUID;

public interface ActionService {

    void create(ActionEntity actionEntity);

    void edit(ActionEntity actionEntity);

    void delete(UUID uuid);

    ActionEntity findByUuid(UUID uuid);

    List<ActionEntity> findAll();

    List<ActionEntity> findAllByPermissionUuid(UUID permissionUuid);

    List<ActionEntity> findAllByUuids(List<UUID> uuids);

}

package com.erp.repository;

import com.erp.entity.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {

    Optional<ActionEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    List<ActionEntity> findByIsDeletedFalse();

    List<ActionEntity> findByIsDeletedFalseAndPermissionUuid(UUID permisssionUuid);

    List<ActionEntity> findByUuidIn(List<UUID> uuids);

}

package com.erp.repository;

import com.erp.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByIsDeletedFalseAndName(String name);

    Optional<PermissionEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    List<PermissionEntity> findByIsDeletedFalse();

    List<PermissionEntity> findByIsDeletedFalseAndUuidIn(List<UUID> uuids);

}

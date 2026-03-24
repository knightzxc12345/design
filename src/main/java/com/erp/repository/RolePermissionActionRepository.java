package com.erp.repository;

import com.erp.entity.RolePermissionActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolePermissionActionRepository extends JpaRepository<RolePermissionActionEntity, Long> {

    List<RolePermissionActionEntity> findByIsDeletedFalse();

    List<RolePermissionActionEntity> findByIsDeletedFalseAndPermissionUuid(UUID permissionUuid);

    List<RolePermissionActionEntity> findByIsDeletedFalseAndRoleUuid(UUID roleUuid);

}

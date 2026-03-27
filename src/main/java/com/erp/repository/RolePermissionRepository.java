package com.erp.repository;

import com.erp.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {

    List<RolePermissionEntity> findByIsDeletedFalse();

    List<RolePermissionEntity> findByIsDeletedFalseAndPermissionUuid(UUID permissionUuid);

    List<RolePermissionEntity> findByIsDeletedFalseAndRoleUuid(UUID roleUuid);

}

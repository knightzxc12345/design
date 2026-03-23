package com.erp.repository;

import com.erp.entity.RolePermissionActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionActionRepository extends JpaRepository<RolePermissionActionEntity, Long> {



}

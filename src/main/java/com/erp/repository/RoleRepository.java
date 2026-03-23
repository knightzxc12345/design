package com.erp.repository;

import com.erp.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByIsDeletedFalseAndName(String name);

    Optional<RoleEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    List<RoleEntity> findByIsDeletedFalse();

}

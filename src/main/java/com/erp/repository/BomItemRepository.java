package com.erp.repository;

import com.erp.entity.BomItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BomItemRepository extends JpaRepository<BomItemEntity, Long> {

    Optional<BomItemEntity> findByIsDeletedFalseAndBomUuidAndMaterialUuid(UUID bomUuid, UUID materialUuid);

    Optional<BomItemEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    List<BomItemEntity> findByIsDeletedFalseAndBomUuid(UUID bomUuid);

}

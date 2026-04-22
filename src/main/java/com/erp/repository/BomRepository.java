package com.erp.repository;

import com.erp.entity.BomEntity;
import com.erp.entity.enums.BomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BomRepository extends JpaRepository<BomEntity, Long> {

    Optional<BomEntity> findByIsDeletedFalseAndItemUuidAndVersion(UUID itemUuid, String version);

    Optional<BomEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    @Query(value =
            """
            SELECT
                b
            FROM
                BomEntity b
            WHERE
                b.isDeleted = false
                AND (b.itemUuid = :itemUuid)
                AND
                (
                    :keyword IS NULL
                )
                AND (b.status = :status)
            ORDER BY
                b.pk
            """)
    List<BomEntity> findAll(
            @Param("itemUuid") UUID itemUuid,
            @Param("keyword") String keyword,
            @Param("status") BomStatus status
    );

    @Query(value =
            """
            SELECT
                b
            FROM
                BomEntity b
            WHERE
                b.isDeleted = false
                AND (b.itemUuid = :itemUuid)
                AND
                (
                    :keyword IS NULL
                )
                AND (b.status = :status)
            ORDER BY
                b.pk
            """)
    Page<BomEntity> findPage(
            Pageable pageable,
            @Param("itemUuid") UUID itemUuid,
            @Param("keyword") String keyword,
            @Param("status") BomStatus status
    );

}

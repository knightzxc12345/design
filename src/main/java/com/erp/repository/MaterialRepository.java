package com.erp.repository;

import com.erp.entity.MaterialEntity;
import com.erp.entity.enums.MaterialStatus;
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
public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {

    Optional<MaterialEntity> findByIsDeletedFalseAndSupplierUuidAndCode(UUID supplierUuid, String code);

    Optional<MaterialEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    @Query(value =
            """
            SELECT
                m
            FROM
                MaterialEntity m
            WHERE
                m.isDeleted = false
                AND (m.supplierUuid = :supplierUuid)
                AND
                (
                    :keyword IS NULL OR
                    m.code LIKE CONCAT('%', :keyword, '%') OR
                    m.name LIKE CONCAT('%', :keyword, '%')
                )
                AND (m.status = :status)
            ORDER BY
                m.pk
            """)
    List<MaterialEntity> findAll(
            @Param("supplierUuid") UUID supplierUuid,
            @Param("keyword") String keyword,
            @Param("status") MaterialStatus status
    );

    @Query(value =
            """
            SELECT
                m
            FROM
                MaterialEntity m
            WHERE
                m.isDeleted = false
                AND (m.supplierUuid = :supplierUuid)
                AND
                (
                    :keyword IS NULL OR
                    m.code LIKE CONCAT('%', :keyword, '%') OR
                    m.name LIKE CONCAT('%', :keyword, '%')
                )
                AND (m.status = :status)
            ORDER BY
                m.pk
            """)
    Page<MaterialEntity> findPage(
            Pageable pageable,
            @Param("supplierUuid") UUID supplierUuid,
            @Param("keyword") String keyword,
            @Param("status") MaterialStatus status
    );

}

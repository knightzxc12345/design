package com.erp.repository;

import com.erp.entity.CategoryEntity;
import com.erp.entity.enums.CategoryStatus;
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
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByIsDeletedFalseAndBrandUuidAndName(UUID brandUuid, String name);

    Optional<CategoryEntity> findByIsDeletedFalseAndBrandUuidAndCode(UUID brandUuid, String code);

    Optional<CategoryEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    List<CategoryEntity> findByIsDeletedFalseAndBrandUuid(UUID brandUuid);

    @Query(value =
            """
            SELECT
                c
            FROM
                CategoryEntity c
            WHERE
                c.isDeleted = false
                AND (c.brandUuid = :brandUuid)
                AND
                (
                    :keyword IS NULL OR
                    c.name LIKE CONCAT('%', :keyword, '%') OR
                    c.code LIKE CONCAT('%', :keyword, '%')
                )
                AND (c.status = :status)
            ORDER BY
                c.pk
            """)
    List<CategoryEntity> findAll(
            @Param("brandUuid") UUID brandUuid,
            @Param("keyword") String keyword,
            @Param("status") CategoryStatus status
    );

    @Query(value =
            """
            SELECT
                c
            FROM
                CategoryEntity c
            WHERE
                c.isDeleted = false
                AND (c.brandUuid = :brandUuid)
                AND
                (
                    :keyword IS NULL OR
                    c.name LIKE CONCAT('%', :keyword, '%') OR
                    c.code LIKE CONCAT('%', :keyword, '%')
                )
                AND (c.status = :status)
            ORDER BY
                c.pk
            """)
    Page<CategoryEntity> findPage(
            Pageable pageable,
            @Param("brandUuid") UUID brandUuid,
            @Param("keyword") String keyword,
            @Param("status") CategoryStatus status
    );

}

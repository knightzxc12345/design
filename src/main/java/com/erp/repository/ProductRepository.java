package com.erp.repository;

import com.erp.entity.ProductEntity;
import com.erp.entity.enums.ProductStatus;
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
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByIsDeletedFalseAndCategoryUuidAndName(UUID categoryUuid, String name);

    Optional<ProductEntity> findByIsDeletedFalseAndCategoryUuidAndCode(UUID categoryUuid, String code);

    Optional<ProductEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    List<ProductEntity> findByIsDeletedFalseAndCategoryUuid(UUID categoryUuid);

    @Query(value =
            """
            SELECT
                p
            FROM
                ProductEntity p
            WHERE
                c.isDeleted = false
                AND (c.brandUuid = :brandUuid)
                AND (c.categoryUuid = :categoryUuid)
                AND
                (
                    :keyword IS NULL OR
                    p.name LIKE CONCAT('%', :keyword, '%') OR
                    p.code LIKE CONCAT('%', :keyword, '%')
                )
                AND (p.status = :status)
            ORDER BY
                p.pk
            """)
    List<ProductEntity> findAll(
            @Param("brandUuid") UUID brandUuid,
            @Param("categoryUuid") UUID categoryUuid,
            @Param("keyword") String keyword,
            @Param("status") ProductStatus status
    );

    @Query(value =
            """
            SELECT
                p
            FROM
                ProductEntity p
            WHERE
                c.isDeleted = false
                AND (c.brandUuid = :brandUuid)
                AND (c.categoryUuid = :categoryUuid)
                AND
                (
                    :keyword IS NULL OR
                    p.name LIKE CONCAT('%', :keyword, '%') OR
                    p.code LIKE CONCAT('%', :keyword, '%')
                )
                AND (p.status = :status)
            ORDER BY
                p.pk
            """)
    Page<ProductEntity> findPage(
            Pageable pageable,
            @Param("brandUuid") UUID brandUuid,
            @Param("categoryUuid") UUID categoryUuid,
            @Param("keyword") String keyword,
            @Param("status") ProductStatus status
    );

}

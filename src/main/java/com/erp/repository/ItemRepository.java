package com.erp.repository;

import com.erp.entity.ItemEntity;
import com.erp.entity.enums.BrandStatus;
import com.erp.entity.enums.ItemStatus;
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
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByIsDeletedFalseAndProductUuidAndSkuCode(UUID productUuid, String skuCode);

    Optional<ItemEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    @Query(value =
            """
            SELECT
                i
            FROM
                ItemEntity i
            WHERE
                i.isDeleted = false
                AND (i.productUuid = :productUuid)
                AND
                (
                    :keyword IS NULL OR
                    i.skuCode LIKE CONCAT('%', :keyword, '%')
                )
                AND (i.status = :status)
            ORDER BY
                i.pk
            """)
    List<ItemEntity> findAll(
            @Param("productUuid") UUID productUuid,
            @Param("keyword") String keyword,
            @Param("status") ItemStatus status
    );

    @Query(value =
            """
            SELECT
                i
            FROM
                ItemEntity i
            WHERE
                i.isDeleted = false
                AND (i.productUuid = :productUuid)
                AND
                (
                    :keyword IS NULL OR
                    i.skuCode LIKE CONCAT('%', :keyword, '%')
                )
                AND (i.status = :status)
            ORDER BY
                i.pk
            """)
    Page<ItemEntity> findPage(
            Pageable pageable,
            @Param("productUuid") UUID productUuid,
            @Param("keyword") String keyword,
            @Param("status") ItemStatus status
    );

}

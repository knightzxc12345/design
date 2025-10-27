package com.design.repository;

import com.design.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByNoAndNameAndSupplier_UuidAndIsDeletedFalse(String no, String name, String uuid);

    Optional<ItemEntity> findByNoAndNameAndUuidNotAndSupplier_UuidAndIsDeletedFalse(String no, String name, String supplierUuid, String uuid);

    Optional<ItemEntity> findByUuidAndIsDeletedFalse(String uuid);

    List<ItemEntity> findByIsDeletedFalseAndUuidIn(List<String> uuids);

    @Query(value =
            """
            SELECT
                i
            FROM
                ItemEntity i
            WHERE
                1 = 1
                AND i.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR i.no LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR i.name LIKE CONCAT('%', :keyword, '%'))
                )
                AND (:supplierUuid IS NULL OR :supplierUuid = '' OR i.supplier.uuid = :supplierUuid)
            ORDER BY
                i.no,
                i.supplier.uuid,
                i.name
            """)
    List<ItemEntity> findAll(
            @Param("keyword") String keyword,
            @Param("supplierUuid") String supplierUuid
    );

    @Query(value =
            """
            SELECT
                i
            FROM
                ItemEntity i
            WHERE
                1 = 1
                AND i.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR i.no LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR i.name LIKE CONCAT('%', :keyword, '%'))
                )
                AND (:supplierUuid IS NULL OR :supplierUuid = '' OR i.supplier.uuid = :supplierUuid)
            ORDER BY
                i.supplier.uuid,
                i.no,
                i.name
            """,
            countQuery = """
            SELECT 
                COUNT(i)
            FROM 
                ItemEntity i
            WHERE 
                i.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR i.no LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR i.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:supplierUuid IS NULL OR i.supplier.uuid = :supplierUuid)
                )
            """)
    Page<ItemEntity> findByPage(
            @Param("keyword") String keyword,
            @Param("supplierUuid") String supplierUuid,
            Pageable pageable
    );

}

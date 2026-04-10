package com.erp.repository;

import com.erp.entity.CustomerEntity;
import com.erp.entity.SupplierEntity;
import com.erp.entity.enums.SupplierStatus;
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
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    Optional<SupplierEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    Optional<SupplierEntity> findByIsDeletedFalseAndName(String name);

    Optional<SupplierEntity> findByIsDeletedFalseAndCode(String code);

    @Query(value =
            """
            SELECT
                s
            FROM
                SupplierEntity s
            WHERE
                1 = 1
                AND s.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR s.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.code LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.taxId LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.phone LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.fax LIKE CONCAT('%', :keyword, '%'))
                )
                AND (s.status = :status)
            ORDER BY
                s.name
            """)
    List<SupplierEntity> findAll(
            @Param("keyword") String keyword,
            @Param("SupplierStatus") SupplierStatus status
    );

    @Query(value =
            """
            SELECT
                s
            FROM
                SupplierEntity s
            WHERE
                1 = 1
                AND s.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR s.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.code LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.taxId LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.phone LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.fax LIKE CONCAT('%', :keyword, '%'))
                )
                AND (s.status = :status)
            ORDER BY
                s.name
            """)
    Page<SupplierEntity> findByPage(
            Pageable pageable,
            @Param("keyword") String keyword,
            @Param("SupplierStatus") SupplierStatus status
    );

}

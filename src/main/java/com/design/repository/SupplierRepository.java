package com.design.repository;

import com.design.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    Optional<SupplierEntity> findByNameAndIsDeletedFalse(String name);

    Optional<SupplierEntity> findByNameAndUuidNotAndIsDeletedFalse(String name, String uuid);

    Optional<SupplierEntity> findByUuidAndIsDeletedFalse(String uuid);

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
                    (:keyword IS NULL OR s.vatNumber LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.phone LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                s.name
            """)
    List<SupplierEntity> findAll(
            @Param("keyword") String keyword
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
                    (:keyword IS NULL OR s.vatNumber LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR s.phone LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                s.name
            """)
    Page<SupplierEntity> findByPage(
            @Param("keyword") String keyword,
            Pageable pageable
    );

}

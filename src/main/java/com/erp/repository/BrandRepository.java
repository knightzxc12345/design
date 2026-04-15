package com.erp.repository;

import com.erp.entity.BrandEntity;
import com.erp.entity.enums.BrandStatus;
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
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByIsDeletedFalseAndName(String name);

    Optional<BrandEntity> findByIsDeletedFalseAndCode(String code);

    Optional<BrandEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    @Query(value =
            """
            SELECT
                b
            FROM
                BrandEntity b
            WHERE
                b.isDeleted = false
                AND
                (
                    :keyword IS NULL OR
                    b.name LIKE CONCAT('%', :keyword, '%') OR
                    b.code LIKE CONCAT('%', :keyword, '%')
                )
                AND (b.status = :status)
            ORDER BY
                b.pk
            """)
    List<BrandEntity> findAll(
            @Param("keyword") String keyword,
            @Param("status") BrandStatus status
    );

    @Query(value =
            """
            SELECT
                b
            FROM
                BrandEntity b
            WHERE
                b.isDeleted = false
                AND
                (
                    :keyword IS NULL OR
                    b.name LIKE CONCAT('%', :keyword, '%') OR
                    b.code LIKE CONCAT('%', :keyword, '%')
                )
                AND (b.status = :status)
            ORDER BY
                b.pk
            """)
    Page<BrandEntity> findByPage(
            Pageable pageable,
            @Param("keyword") String keyword,
            @Param("status") BrandStatus status
    );

}

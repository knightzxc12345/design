package com.design.repository;

import com.design.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByCodeAndIsDeletedFalse(String code);

    Optional<ProductEntity> findByCodeAndUuidNotAndIsDeletedFalse(String code, String uuid);

    Optional<ProductEntity> findByUuidAndIsDeletedFalse(String uuid);

    @Query(value =
            """
            SELECT
                p
            FROM
                ProductEntity p
            WHERE
                1 = 1
                AND p.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR p.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR p.code LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                p.code
            """)
    List<ProductEntity> findAll(
            @Param("keyword") String keyword
    );

    @Query(value =
            """
            SELECT
                p
            FROM
                ProductEntity p
            WHERE
                1 = 1
                AND p.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR p.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR p.code LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                i.code
            """)
    Page<ProductEntity> findByPage(
            @Param("keyword") String keyword,
            Pageable pageable
    );

}

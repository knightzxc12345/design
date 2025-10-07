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

    Optional<ItemEntity> findByCodeAndIsDeletedFalse(String code);

    Optional<ItemEntity> findByCodeAndUuidNotAndIsDeletedFalse(String code, String uuid);

    Optional<ItemEntity> findByUuidAndIsDeletedFalse(String uuid);

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
                    (:keyword IS NULL OR i.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR i.code LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                i.code
            """)
    List<ItemEntity> findAll(
            @Param("keyword") String keyword
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
                    (:keyword IS NULL OR i.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR i.code LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                i.code
            """)
    Page<ItemEntity> findByPage(
            @Param("keyword") String keyword,
            Pageable pageable
    );

}

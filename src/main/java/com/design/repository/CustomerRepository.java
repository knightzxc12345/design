package com.design.repository;

import com.design.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByNameAndIsDeletedFalse(String name);

    Optional<CustomerEntity> findByNameAndUuidNotAndIsDeletedFalse(String name, String uuid);

    Optional<CustomerEntity> findByUuidAndIsDeletedFalse(String uuid);

    @Query(value =
            """
            SELECT
                c
            FROM
                CustomerEntity c
            WHERE
                1 = 1
                AND c.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR c.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR c.vatNumber LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR c.phone LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                c.name
            """)
    List<CustomerEntity> findAll(
            @Param("keyword") String keyword
    );

    @Query(value =
            """
            SELECT
                c
            FROM
                CustomerEntity c
            WHERE
                1 = 1
                AND c.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR c.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR c.vatNumber LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR c.phone LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                c.name
            """)
    Page<CustomerEntity> findByPage(
            @Param("keyword") String keyword,
            Pageable pageable
    );

}

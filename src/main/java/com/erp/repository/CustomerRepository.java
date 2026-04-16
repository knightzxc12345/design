package com.erp.repository;

import com.erp.entity.CustomerEntity;
import com.erp.entity.enums.CustomerStatus;
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
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByUuidAndIsDeletedFalse(UUID uuid);

    Optional<CustomerEntity> findByIsDeletedFalseAndName(String name);

    Optional<CustomerEntity> findByIsDeletedFalseAndEmail(String email);

    @Query(value =
            """
            SELECT
                c
            FROM
                CustomerEntity c
            WHERE
                c.isDeleted = false
                AND
                (
                    :keyword IS NULL OR
                    c.name LIKE CONCAT('%', :keyword, '%') OR
                    c.vatNumber LIKE CONCAT('%', :keyword, '%') OR
                    c.phone LIKE CONCAT('%', :keyword, '%')
                )
                AND (c.status = :status)
            ORDER BY
                c.name
            """)
    List<CustomerEntity> findAll(
            @Param("keyword") String keyword,
            @Param("CustomerStatus") CustomerStatus status
    );

    @Query(value =
            """
            SELECT
                c
            FROM
                CustomerEntity c
            WHERE
                c.isDeleted = false
                AND
                (
                    :keyword IS NULL OR
                    c.name LIKE CONCAT('%', :keyword, '%') OR
                    c.vatNumber LIKE CONCAT('%', :keyword, '%') OR
                    c.phone LIKE CONCAT('%', :keyword, '%')
                )
                AND (c.status = :status)
            ORDER BY
                c.name
            """)
    Page<CustomerEntity> findPage(
            Pageable pageable,
            @Param("keyword") String keyword,
            @Param("CustomerStatus") CustomerStatus status
    );

}

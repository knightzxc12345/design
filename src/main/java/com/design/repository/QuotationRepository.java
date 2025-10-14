package com.design.repository;

import com.design.entity.QuotationEntity;
import com.design.entity.enums.QuotationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, Long> {

    @Query(value = """
            SELECT 
                DISTINCT q
            FROM 
                QuotationEntity q
            LEFT JOIN FETCH 
                q.customer
            LEFT JOIN FETCH 
                q.items pi
            LEFT JOIN FETCH 
                pi.item
            WHERE 
                q.uuid = :uuid 
            """)
    Optional<QuotationEntity> findByUuid(
            String uuid
    );

    @Query(value =
            """
            SELECT
                q
            FROM
                QuotationEntity q
            LEFT JOIN 
                q.customer c
            WHERE
                1 = 1
                AND (:quotationStatus IS NULL OR q.status = :quotationStatus)
                AND (COALESCE(:startTime, q.createTime) <= q.createTime)
                AND (COALESCE(:endTime, q.createTime) >= q.createTime)
                AND
                (
                    (:keyword IS NULL OR q.quotationNo LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR c.name LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                q.createTime DESC
            """)
    List<QuotationEntity> findAll(
            @Param("keyword") String keyword,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime,
            @Param("quotationStatus") QuotationStatus quotationStatus
    );

    @Query(value =
            """
            SELECT
                q
            FROM
                QuotationEntity q
            LEFT JOIN 
                q.customer c
            WHERE
                1 = 1
                AND (:quotationStatus IS NULL OR q.status = :quotationStatus)
                AND (COALESCE(:startTime, q.createTime) <= q.createTime)
                AND (COALESCE(:endTime, q.createTime) >= q.createTime)
                AND
                (
                    (:keyword IS NULL OR q.quotationNo LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR c.name LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                q.createTime DESC
            """)
    Page<QuotationEntity> findByPage(
            @Param("keyword") String keyword,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime,
            @Param("quotationStatus") QuotationStatus quotationStatus,
            Pageable pageable
    );

}

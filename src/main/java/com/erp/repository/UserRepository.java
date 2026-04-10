package com.erp.repository;

import com.erp.entity.UserEntity;
import com.erp.entity.enums.SupplierStatus;
import com.erp.entity.enums.UserStatus;
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
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIsDeletedFalseAndUuid(UUID uuid);

    Optional<UserEntity> findByIsDeletedFalseAndAccount(String account);

    Optional<UserEntity> findByIsDeletedFalseAndName(String name);

    Optional<UserEntity> findByIsDeletedFalseAndEmail(String email);

    @Query(value =
            """
            SELECT
                u
            FROM
                UserEntity u
            WHERE
                1 = 1
                AND u.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR u.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR u.email LIKE CONCAT('%', :keyword, '%'))
                )
                AND (u.status = :status)
            ORDER BY
                u.pk
            """)
    List<UserEntity> findAll(
            @Param("keyword") String keyword,
            @Param("UserStatus") UserStatus status
    );

    @Query(value =
            """
            SELECT
                u
            FROM
                UserEntity u
            WHERE
                1 = 1
                AND u.isDeleted = false
                AND
                (
                    (:keyword IS NULL OR u.name LIKE CONCAT('%', :keyword, '%')) OR
                    (:keyword IS NULL OR u.email LIKE CONCAT('%', :keyword, '%'))
                )
                AND (u.status = :status)
            ORDER BY
                u.pk
            """)
    Page<UserEntity> findByPage(
            Pageable pageable,
            @Param("keyword") String keyword,
            @Param("UserStatus") UserStatus status
    );

}

package com.erp.repository;

import com.erp.entity.UserEntity;
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

    Optional<UserEntity> findByIsDeletedFalseAndUuidAndAccount(String account);

    Optional<UserEntity> findByIsDeletedFalseAndUuidAndName(String name);

    Optional<UserEntity> findByIsDeletedFalseAndUuidAndEmail(String email);

    Optional<UserEntity> findByIsDeletedFalseAndAccount(String account);

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
            ORDER BY
                u.pk
            """)
    List<UserEntity> findAll(
            @Param("keyword") String keyword
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
            ORDER BY
                u.pk
            """)
    Page<UserEntity> findByPage(
            Pageable pageable,
            @Param("keyword") String keyword
    );

}

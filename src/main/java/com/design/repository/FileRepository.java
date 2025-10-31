package com.design.repository;

import com.design.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByUuidOrderByUuidAsc(String uuid);

    @Query(value =
            """
            SELECT
                f
            FROM
                FileEntity f
            WHERE
                1 = 1
                AND
                (
                    (:keyword IS NULL OR f.tag LIKE CONCAT('%', :keyword, '%'))
                )
            ORDER BY
                f.pk DESC
            """)
    Page<FileEntity> findByPage(
            @Param("keyword") String keyword,
            Pageable pageable
    );

}

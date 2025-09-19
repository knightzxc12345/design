package com.design.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@ToString
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "pk",
            nullable = false,
            updatable = false,
            unique = true
    )
    private Long pk;

    // 唯一值
    @Column(
            name = "uuid",
            nullable = false,
            updatable = false,
            unique = true,
            length = 36
    )
    @NotNull
    private UUID uuid;

    // 創建時間
    @CreatedDate
    @Column(
            name = "create_time",
            nullable = false,
            updatable = false
    )
    @NotNull
    private Instant createTime;

    // 創建人員
    @Column(
            name = "create_user",
            nullable = false,
            updatable = false,
            length = 36
    )
    @NotNull
    private UUID createUser;

    // 編輯時間
    @LastModifiedDate
    @Column(
            name = "modified_time"
    )
    private Instant modifiedTime;

    // 編輯人員
    @Column(
            name = "modified_user",
            length = 36
    )
    private UUID modifiedUser;

}
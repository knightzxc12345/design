package com.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

// 使用者角色
@Table(name = "user_role", indexes = {
        @Index(name = "user_role_find", columnList = "uuid"),
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRoleEntity extends BaseEntity {

    // 使用者id
    @Column(
            name = "user_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID userUuid;

    // 角色id
    @Column(
            name = "role_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID roleUuid;

    // 是否刪除
    @Column(
            name = "is_deleted",
            nullable = false
    )
    @NotNull
    private Boolean isDeleted = false;

    // 刪除時間
    @Column(
            name = "deleted_time"
    )
    private Instant deletedTime;

    // 刪除人員
    @Column(
            name = "deleted_user",
            length = 36
    )
    private UUID deletedUser;

}

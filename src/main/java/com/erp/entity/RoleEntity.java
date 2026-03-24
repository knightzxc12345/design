package com.erp.entity;

import com.erp.entity.enums.RoleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

// 角色
@ToString(callSuper = true)
@Data
@Table(name = "role", indexes = {
        @Index(name = "role_find", columnList = "uuid, is_deleted"),
        @Index(name = "role_find_all", columnList = "pk, is_deleted")
})
@Entity
public class RoleEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "user_name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 狀態
    @Column(
            name = "status",
            nullable = false,
            length = 32
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleStatus status;

    // 是否刪除
    @Column(
            name = "is_deleted",
            nullable = false
    )
    @NotNull
    private Boolean isDeleted;

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

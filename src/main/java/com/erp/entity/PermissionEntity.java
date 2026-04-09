package com.erp.entity;

import com.erp.entity.enums.PermissionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Table(name = "permission", indexes = {
        @Index(name = "permission_find", columnList = "uuid"),
        @Index(name = "permission_find", columnList = "parent_uuid"),
        @Index(name = "permission_find_all", columnList = "pk")
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PermissionEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 32
    )
    @NotBlank
    private String name;

    // 代碼
    @Column(
            name = "code",
            nullable = false,
            length = 32
    )
    @NotBlank
    private String code;

    // 父權線Uuid
    @Column(
            name = "parent_uuid",
            length = 36
    )
    private UUID parentUuid;

    // 排序
    @Column(
            name = "sort",
            nullable = false
    )
    @NotNull
    private Integer sort;

    // 狀態
    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private PermissionStatus status;

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
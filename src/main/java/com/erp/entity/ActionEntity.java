package com.erp.entity;

import com.erp.entity.enums.ActionMethod;
import com.erp.entity.enums.ActionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@ToString(callSuper = true)
@Data
@Table(name = "action", indexes = {
        @Index(name = "action_find", columnList = "uuid"),
        @Index(name = "action_find_all", columnList = "pk")
})
@Entity
public class ActionEntity extends BaseEntity {

    // 權限id
    @Column(
            name = "permission_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID permissionUuid;

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 32
    )
    @NotBlank
    private String name;

    // 方法
    @Column(
            name = "method",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private ActionMethod method;

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
    private ActionStatus status;


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
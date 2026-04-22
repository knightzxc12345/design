package com.erp.entity;

import com.erp.entity.enums.MaterialStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

// 材料
@Table(name = "material", indexes = {
        @Index(name = "material_find", columnList = "uuid, is_deleted"),
        @Index(name = "material_find_all", columnList = "pk, is_deleted")
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MaterialEntity extends BaseEntity {

    // 供應商id
    @Column(
            name = "supplier_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID supplierUuid;

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 代碼
    @Column(
            name = "code",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String code;

    // 規格
    @Column(
            name = "spec",
            length = 128
    )
    private String spec;

    // 單位
    @Column(
            name = "unit",
            length = 10
    )
    private String unit;

    // 成本
    @Column(
            name = "cost",
            nullable = false
    )
    @NotBlank
    private BigDecimal cost;

    // 備註
    @Column(
            name = "remark",
            columnDefinition = "TEXT"
    )
    private String remark;

    // 狀態
    @Column(
            name = "status",
            nullable = false,
            length = 32
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private MaterialStatus status;

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

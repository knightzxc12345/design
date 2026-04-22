package com.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

// BOM
@Table(name = "bom_item", indexes = {
        @Index(name = "bom_item_find", columnList = "uuid, is_deleted"),
        @Index(name = "bom_item_find_all", columnList = "pk, is_deleted")
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BomItemEntity extends BaseEntity {

    // BOM id
    @Column(
            name = "bom_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID bomUuid;

    // 材料 id
    @Column(
            name = "material_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID materialUuid;

    // 數量
    @Column(
            name = "quantity",
            nullable = false
    )
    @NotBlank
    private BigDecimal quantity;

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

package com.erp.entity;

import com.erp.entity.enums.BomStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

// BOM
@Table(name = "bom", indexes = {
        @Index(name = "bom_find", columnList = "uuid, is_deleted"),
        @Index(name = "bom_find_all", columnList = "pk, is_deleted")
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BomEntity extends BaseEntity {

    // 項目id
    @Column(
            name = "item_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID itemUuid;

    // 版本
    @Column(
            name = "version",
            nullable = false,
            length = 5
    )
    @NotBlank
    private String version;

    // 狀態
    @Column(
            name = "status",
            nullable = false,
            length = 32
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private BomStatus status;

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

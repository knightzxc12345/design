package com.erp.entity;

import com.erp.entity.enums.SupplierStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

// 供應商
@Table(name = "supplier", indexes = {
        @Index(name = "supplier_find", columnList = "uuid, is_deleted"),
        @Index(name = "supplier_find_all", columnList = "pk, is_deleted")
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SupplierEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 代號
    @Column(
            name = "code",
            length = 64
    )
    private String code;

    // 統編
    @Column(
            name = "tax_id",
            length = 8
    )
    private String taxId;

    // 電話
    @Column(
            name = "phone",
            length = 32
    )
    private String phone;

    // 傳真
    @Column(
            name = "fax",
            length = 32
    )
    private String fax;

    // 登記地址
    @Column(
            name = "register_address",
            columnDefinition = "TEXT"
    )
    private String registerAddress;

    // 營業地址
    @Column(
            name = "business_address",
            columnDefinition = "TEXT"
    )
    private String businessAddress;

    // 狀態
    @Column(
            name = "status",
            nullable = false,
            length = 32
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private SupplierStatus status;

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

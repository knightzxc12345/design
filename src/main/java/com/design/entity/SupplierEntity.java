package com.design.entity;

import com.design.base.regex.BaseRegex;
import com.design.entity.enums.SupplierStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

// 供應商
@ToString(callSuper = true)
@Data
@Table(name = "supplier", indexes = {
        @Index(name = "vendor_find", columnList = "uuid, is_deleted"),
        @Index(name = "vendor_find_all", columnList = "pk, is_deleted")
})
@Entity
public class SupplierEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 統一編號
    @Column(
            name = "vat_number",
            nullable = true,
            length = 8
    )
    @Pattern(regexp = BaseRegex.EMPTY + BaseRegex.VAT_NUMBER)
    private String vatNumber;

    // 電話
    @Column(
            name = "phone",
            length = 20
    )
    private String phone;

    // 傳真
    @Column(
            name = "fax",
            length = 32
    )
    private String fax;

    // 聯絡人信箱
    @Column(
            name = "email",
            length = 128
    )
    @Email
    private String email;

    // 地址
    @Column(
            name = "address"
    )
    private String address;

    // 聯絡人
    @Column(
            name = "contact_name",
            length = 64
    )
    private String contactName;

    // 聯絡人電話
    @Column(
            name = "contact_phone",
            length = 32
    )
    private String contactPhone;

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
    private SupplierStatus status;

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
    private String deletedUser;

}

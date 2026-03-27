package com.erp.entity;

import com.erp.entity.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

// 使用者
@Table(name = "user", indexes = {
        @Index(name = "user_find", columnList = "uuid, is_deleted"),
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "user_name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 帳號
    @Column(
            name = "account",
            nullable = false,
            updatable = false,
            length = 32
    )
    @NotBlank
    private String account;

    // 密碼
    @Column(
            name = "password",
            nullable = false,
            length = 128
    )
    @NotBlank
    private String password;

    // 信箱
    @Column(
            name = "email",
            nullable = false,
            length = 128
    )
    @Email
    @NotBlank
    private String email;

    // 手機
    @Column(
            name = "mobile",
            nullable = false,
            length = 10
    )
    private String mobile;

    // 最後登入時間
    @Column(
            name = "last_login_time"
    )
    private Instant lastLoginTime;

    @Column(
            name = "token_version",
            nullable = false
    )
    private long tokenVersion;

    // 狀態
    @Column(
            name = "status",
            nullable = false,
            length = 32
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;

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

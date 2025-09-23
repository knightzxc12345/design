package com.design.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

// 使用者
@ToString(callSuper = true)
@Data
@Table(name = "user", indexes = {
        @Index(name = "user_find", columnList = "uuid, is_deleted"),
})
@Entity
public class UserEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "user_name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String username;

    // 密碼
    @Column(
            name = "password",
            nullable = false,
            length = 128
    )
    @NotBlank
    private String password;

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

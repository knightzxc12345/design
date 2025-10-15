package com.design.entity;

import com.design.entity.enums.QuotationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// 報價單
@ToString(callSuper = true)
@Data
@Table(name = "quotation", indexes = {
        @Index(name = "quotation_find", columnList = "uuid"),
        @Index(name = "quotation_find_all", columnList = "pk")
})
@Entity
public class QuotationEntity extends BaseEntity {

    // 單號
    @Column(
            name = "quotation_no",
            nullable = false,
            unique = true,
            length = 64
    )
    @NotBlank
    private String quotationNo;

    // 對應客戶
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_uuid", nullable = false)
    private CustomerEntity customer;

    // 報價單明細
    @OneToMany(
            mappedBy = "quotation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<QuotationProductEntity> products = new ArrayList<>();

    // 成本總計
    @Column(
            name = "total_cost_price",
            nullable = false,
            precision = 15
    )
    @NotBlank
    private BigDecimal totalCostPrice;

    // 報價總計
    @Column(
            name = "total_price",
            nullable = false,
            precision = 15
    )
    @NotBlank
    private BigDecimal totalPrice;

    // 議價總計
    @Column(
            name = "total_negotiated_price",
            nullable = false,
            precision = 15
    )
    @NotBlank
    private BigDecimal totalNegotiatedPrice;

    // 狀態
    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private QuotationStatus status;

}

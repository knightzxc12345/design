package com.design.service;

import com.design.entity.QuotationEntity;
import com.design.entity.enums.QuotationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface QuotationService {

    void create(QuotationEntity quotationEntity);

    void edit(QuotationEntity quotationEntity);

    QuotationEntity findByUuid(String uuid);

    List<QuotationEntity> findAll(
            String keyword,
            Instant startTime,
            Instant endTime,
            QuotationStatus quotationStatus
    );

    Page<QuotationEntity> findByPage(
            String keyword,
            Instant startTime,
            Instant endTime,
            QuotationStatus quotationStatus,
            Pageable pageable
    );

}

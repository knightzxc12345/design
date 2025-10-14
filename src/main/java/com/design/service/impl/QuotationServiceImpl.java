package com.design.service.impl;

import com.design.base.api.QuotationCode;
import com.design.entity.QuotationEntity;
import com.design.entity.enums.QuotationStatus;
import com.design.handler.BusinessException;
import com.design.repository.QuotationRepository;
import com.design.service.QuotationService;
import com.design.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;

    @Override
    public void create(QuotationEntity quotationEntity) {
        quotationEntity.setUuid(UUID.randomUUID().toString());
        quotationEntity.setStatus(QuotationStatus.DRAFT);
        quotationEntity.setCreateTime(Instant.now());
        quotationEntity.setCreateUser(UserUtil.getUserUuid());
        quotationRepository.save(quotationEntity);
    }

    @Override
    public void edit(QuotationEntity quotationEntity) {
        quotationEntity.setModifiedTime(Instant.now());
        quotationEntity.setModifiedUser(UserUtil.getUserUuid());
        quotationRepository.save(quotationEntity);
    }

    @Override
    public QuotationEntity findByUuid(String uuid) {
        return quotationRepository.findByUuid(uuid)
                .orElseThrow(() -> new BusinessException(QuotationCode.NOT_EXISTS));
    }

    @Override
    public List<QuotationEntity> findAll(
            String keyword,
            Instant startTime,
            Instant endTime,
            QuotationStatus quotationStatus) {
        return quotationRepository.findAll(
                keyword,
                startTime,
                endTime,
                quotationStatus
        );
    }

    @Override
    public Page<QuotationEntity> findByPage(
            String keyword,
            Instant startTime,
            Instant endTime,
            QuotationStatus quotationStatus,
            Pageable pageable) {
        return quotationRepository.findByPage(
                keyword,
                startTime,
                endTime,
                quotationStatus,
                pageable
        );
    }

}

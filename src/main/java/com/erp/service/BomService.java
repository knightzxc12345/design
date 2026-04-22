package com.erp.service;

import com.erp.entity.BomEntity;
import com.erp.entity.enums.BomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BomService {

    BomEntity create(BomEntity bomEntity);

    BomEntity edit(BomEntity bomEntity);

    BomEntity delete(UUID uuid);

    BomEntity findByUuid(UUID uuid);

    List<BomEntity> findAll(
            UUID itemUuid,
            String keyword,
            BomStatus bomStatus
    );

    Page<BomEntity> findPage(
            Pageable pageable,
            UUID itemUuid,
            String keyword,
            BomStatus bomStatus
    );

}

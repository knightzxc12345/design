package com.erp.usecase.brand.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.brand.request.BrandFindRequest;
import com.erp.controller.brand.request.BrandPageRequest;
import com.erp.controller.brand.response.BrandFindAllResponse;
import com.erp.controller.brand.response.BrandFindResponse;
import com.erp.entity.BrandEntity;
import com.erp.service.BrandService;
import com.erp.usecase.brand.BrandFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandFindUseCaseImpl implements BrandFindUseCase {

    private final BrandService brandService;

    @Override
    public BrandFindResponse findDetail(UUID uuid) {
        BrandEntity brandEntity = brandService.findByUuid(uuid);
        return new BrandFindResponse(
                brandEntity.getUuid(),
                brandEntity.getName(),
                brandEntity.getCode(),
                brandEntity.getDescription(),
                brandEntity.getStatus()
        );
    }

    @Override
    public List<BrandFindAllResponse> findAll(BrandFindRequest request) {
        List<BrandEntity> brandEntities = brandService.findAll(request.keyword(), request.status());
        return formatList(brandEntities);
    }

    @Override
    public PageResponse<BrandFindAllResponse> findByPage(BrandPageRequest request) {
        Page<BrandEntity> brandEntityPage = brandService.findPage(
                PageRequest.of(request.page(), request.size()),
                request.keyword(),
                request.status()
        );
        return formatPage(brandEntityPage);
    }

    private List<BrandFindAllResponse> formatList(List<BrandEntity> brandEntities) {
        if (null != brandEntities || brandEntities.isEmpty()) {
            return List.of();
        }
        return brandEntities.stream()
                .map(brandEntity -> new BrandFindAllResponse(
                        brandEntity.getUuid(),
                        brandEntity.getName(),
                        brandEntity.getCode(),
                        brandEntity.getDescription(),
                        brandEntity.getStatus()
                ))
                .toList();
    }

    private PageResponse<BrandFindAllResponse> formatPage(Page<BrandEntity> brandEntityPage) {
        List<BrandFindAllResponse> responses = formatList(brandEntityPage.getContent());
        return new PageResponse<BrandFindAllResponse>(
                brandEntityPage.getNumber(),
                brandEntityPage.getSize(),
                brandEntityPage.getTotalElements(),
                brandEntityPage.getTotalPages(),
                responses
        );
    }

}

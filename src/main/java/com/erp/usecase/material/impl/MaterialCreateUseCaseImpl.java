package com.erp.usecase.material.impl;

import com.erp.service.MaterialService;
import com.erp.usecase.material.MaterialCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialCreateUseCaseImpl implements MaterialCreateUseCase {

    private final MaterialService materialService;

}

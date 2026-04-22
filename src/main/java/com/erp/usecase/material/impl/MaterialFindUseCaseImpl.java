package com.erp.usecase.material.impl;

import com.erp.service.MaterialService;
import com.erp.usecase.material.MaterialFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialFindUseCaseImpl implements MaterialFindUseCase {

    private final MaterialService materialService;

}

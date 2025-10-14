package com.design.service.impl;

import com.design.repository.QuotationRepository;
import com.design.service.QuotationItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuotationItemServiceImpl implements QuotationItemService {

    private final QuotationRepository quotationRepository;

}

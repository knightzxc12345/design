package com.erp.usecase.customer.impl;

import com.erp.base.response.PageResponse;
import com.erp.controller.customer.request.CustomerFindRequest;
import com.erp.controller.customer.request.CustomerPageRequest;
import com.erp.controller.customer.response.CustomerFindAllResponse;
import com.erp.controller.customer.response.CustomerFindResponse;
import com.erp.entity.CustomerEntity;
import com.erp.service.CustomerService;
import com.erp.usecase.customer.CustomerFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerFindUseCaseImpl implements CustomerFindUseCase {

    private final CustomerService customerService;

    @Transactional(readOnly = true)
    @Override
    public CustomerFindResponse findDetail(UUID uuid) {
        CustomerEntity customerEntity = customerService.findByUuid(uuid);
        return new CustomerFindResponse(
                customerEntity.getUuid(),
                customerEntity.getName(),
                customerEntity.getPhone(),
                customerEntity.getFax(),
                customerEntity.getEmail(),
                customerEntity.getAddress(),
                customerEntity.getVatNumber(),
                customerEntity.getContactName(),
                customerEntity.getContactPhone(),
                customerEntity.getRemark(),
                customerEntity.getStatus()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerFindAllResponse> findAll(CustomerFindRequest request) {
        List<CustomerEntity> customerEntities = customerService.findAll(request.keyword(), request.status());
        return formatList(customerEntities);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<CustomerFindAllResponse> findByPage(CustomerPageRequest request) {
        Page<CustomerEntity> customerEntityPage = customerService.findByPage(
                PageRequest.of(request.page(), request.size()),
                request.keyword(),
                request.status()
        );
        return formatPage(customerEntityPage);
    }

    private List<CustomerFindAllResponse> formatList(List<CustomerEntity> customerEntities){
        if(customerEntities == null || customerEntities.isEmpty()){
            return List.of();
        }
        return customerEntities.stream()
                .map(customerEntity -> new CustomerFindAllResponse(
                        customerEntity.getUuid(),
                        customerEntity.getName(),
                        customerEntity.getPhone(),
                        customerEntity.getEmail(),
                        customerEntity.getAddress(),
                        customerEntity.getVatNumber(),
                        customerEntity.getContactName(),
                        customerEntity.getContactPhone(),
                        customerEntity.getStatus()
                ))
                .toList();
    }

    private PageResponse<CustomerFindAllResponse> formatPage(Page<CustomerEntity> customerEntityPage){
        List<CustomerFindAllResponse> responses = formatList(customerEntityPage.getContent());
        return new PageResponse(
                customerEntityPage.getNumber(),
                customerEntityPage.getSize(),
                customerEntityPage.getTotalElements(),
                customerEntityPage.getTotalPages(),
                responses
        );
    }

}

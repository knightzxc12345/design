package com.design.usecase.customer.impl;

import com.design.controller.common.response.PageResponse;
import com.design.controller.customer.request.CustomerFindRequest;
import com.design.controller.customer.request.CustomerPageRequest;
import com.design.controller.customer.response.CustomerFindAllResponse;
import com.design.controller.customer.response.CustomerFindResponse;
import com.design.controller.customer.response.CustomerPageResponse;
import com.design.entity.CustomerEntity;
import com.design.service.CustomerService;
import com.design.usecase.customer.CustomerFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerFindUseCaseImpl implements CustomerFindUseCase {

    private final CustomerService customerService;

    @Override
    public CustomerFindResponse findDetail(String uuid) {
        CustomerEntity customerEntity = customerService.findByUuid(uuid);
        return new CustomerFindResponse(
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

    @Override
    public List<CustomerFindAllResponse> findAll(CustomerFindRequest request) {
        List<CustomerEntity> customerEntities = customerService.findAll(request.keyword());
        return formatList(customerEntities);
    }

    @Override
    public CustomerPageResponse findByPage(CustomerPageRequest request) {
        Page<CustomerEntity> customerEntityPage = customerService.findByPage(
                request.keyword(),
                PageRequest.of(request.page(), request.size())
        );
        return formatPage(customerEntityPage);
    }

    private List<CustomerFindAllResponse> formatList(List<CustomerEntity> customerEntities){
        List<CustomerFindAllResponse> responses = new ArrayList<>();
        if(null == customerEntities || customerEntities.isEmpty()){
            return responses;
        }
        for(CustomerEntity customerEntity : customerEntities){
            responses.add(new CustomerFindAllResponse(
                    customerEntity.getUuid(),
                    customerEntity.getName(),
                    customerEntity.getEmail(),
                    customerEntity.getVatNumber(),
                    customerEntity.getContactName(),
                    customerEntity.getContactPhone(),
                    customerEntity.getStatus()
            ));
        }
        return responses;
    }

    private CustomerPageResponse formatPage(Page<CustomerEntity> customerEntityPage){
        List<CustomerFindAllResponse> responses = formatList(customerEntityPage.getContent());
        return new CustomerPageResponse(
                new PageResponse(
                        customerEntityPage.getNumber(),
                        customerEntityPage.getSize(),
                        customerEntityPage.getTotalPages()
                ),
                responses
        );
    }

}

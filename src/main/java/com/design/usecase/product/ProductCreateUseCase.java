package com.design.usecase.product;

import com.design.controller.product.request.ProductCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProductCreateUseCase {

    void create(ProductCreateRequest request, MultipartFile file);

}

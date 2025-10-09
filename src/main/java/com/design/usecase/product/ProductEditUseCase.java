package com.design.usecase.product;

import com.design.controller.product.request.ProductEditRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProductEditUseCase {

    void edit(String uuid, ProductEditRequest request, MultipartFile file);

}

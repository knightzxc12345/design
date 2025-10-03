package com.design.usecase.item;

import com.design.controller.item.request.ItemCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ItemCreateUseCase {

    void create(ItemCreateRequest request, MultipartFile file);

}

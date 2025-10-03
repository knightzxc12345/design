package com.design.usecase.item;

import com.design.controller.item.request.ItemEditRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ItemEditUseCase {

    void edit(String uuid, ItemEditRequest request, MultipartFile file);

}

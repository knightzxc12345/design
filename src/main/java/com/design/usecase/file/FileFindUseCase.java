package com.design.usecase.file;

import com.design.controller.file.request.FileFindRequest;
import com.design.controller.file.request.FilePageRequest;
import com.design.controller.file.response.FileFindAllResponse;
import com.design.controller.file.response.FilePageResponse;

import java.util.List;

public interface FileFindUseCase {

    List<FileFindAllResponse> findAll(FileFindRequest request);

    FilePageResponse findByPage(FilePageRequest request);

}

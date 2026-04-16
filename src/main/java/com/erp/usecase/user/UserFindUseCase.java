package com.erp.usecase.user;

import com.erp.base.response.PageResponse;
import com.erp.controller.user.request.UserFindRequest;
import com.erp.controller.user.request.UserPageRequest;
import com.erp.controller.user.response.UserFindAllResponse;
import com.erp.controller.user.response.UserFindResponse;

import java.util.List;
import java.util.UUID;

public interface UserFindUseCase {

    UserFindResponse findDetail(UUID uuid);

    List<UserFindAllResponse> findAll(UserFindRequest request);

    PageResponse<UserFindAllResponse> findPage(UserPageRequest request);

}

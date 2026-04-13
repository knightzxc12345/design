package com.erp.controller.customer;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.PageResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.customer.request.CustomerCreateRequest;
import com.erp.controller.customer.request.CustomerEditRequest;
import com.erp.controller.customer.request.CustomerFindRequest;
import com.erp.controller.customer.request.CustomerPageRequest;
import com.erp.controller.customer.response.CustomerFindAllResponse;
import com.erp.controller.customer.response.CustomerFindResponse;
import com.erp.usecase.customer.CustomerCreateUseCase;
import com.erp.usecase.customer.CustomerDeleteUseCase;
import com.erp.usecase.customer.CustomerEditUseCase;
import com.erp.usecase.customer.CustomerFindUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/customer")
@Tag(name = "客戶")
@RestController
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerCreateUseCase customerCreateUseCase;

    private final CustomerEditUseCase customerEditUseCase;

    private final CustomerDeleteUseCase customerDeleteUseCase;

    private final CustomerFindUseCase customerFindUseCase;

    @Permission("CUSTOMER:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull CustomerCreateRequest request) {
        customerCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("CUSTOMER:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull CustomerEditRequest request) {
        customerEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("CUSTOMER:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        customerDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("CUSTOMER:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = CustomerFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        CustomerFindResponse response = customerFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("CUSTOMER:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated CustomerFindRequest request) {
        List<CustomerFindAllResponse> responses = customerFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Permission("CUSTOMER:READ")
    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = PageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @Validated CustomerPageRequest request) {
        PageResponse response = customerFindUseCase.findByPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}

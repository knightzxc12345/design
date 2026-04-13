package com.erp.controller.permission;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.permission.request.PermissionBindRequest;
import com.erp.controller.permission.request.PermissionCreateRequest;
import com.erp.controller.permission.request.PermissionEditRequest;
import com.erp.controller.permission.request.PermissionFindRequest;
import com.erp.controller.permission.response.PermissionFindAllResponse;
import com.erp.usecase.permission.PermissionCreateUseCase;
import com.erp.usecase.permission.PermissionDeleteUseCase;
import com.erp.usecase.permission.PermissionEditUseCase;
import com.erp.usecase.permission.PermissionFindUseCase;
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

@RequestMapping("/permission")
@Tag(name = "權限")
@RestController
@RequiredArgsConstructor
@Validated
public class PermissionController {

    private final PermissionCreateUseCase permissionCreateUseCase;

    private final PermissionEditUseCase permissionEditUseCase;

    private final PermissionDeleteUseCase permissionDeleteUseCase;

    private final PermissionFindUseCase permissionFindUseCase;

    @Permission("PERMISSION:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull PermissionCreateRequest request) {
        permissionCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("PERMISSION:EDIT")
    @Operation(summary = "編輯")
    @PatchMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull PermissionEditRequest request) {
        permissionEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("PERMISSION:BIND")
    @Operation(summary = "綁定")
    @PatchMapping(
            value = "v1/{roleUuid}/bind"
    )
    public CustomResponse bind(
            @PathVariable("roleUuid") @NotNull UUID roleUuid,
            @RequestBody @Validated @NotNull PermissionBindRequest request) {
        permissionEditUseCase.bind(roleUuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("PERMISSION:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        permissionDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("PERMISSION:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = PermissionFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated PermissionFindRequest request) {
        List<PermissionFindAllResponse> responses = permissionFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

}

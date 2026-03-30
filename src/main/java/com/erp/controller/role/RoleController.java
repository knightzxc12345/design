package com.erp.controller.role;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.role.request.RoleCreateRequest;
import com.erp.controller.role.request.RoleEditRequest;
import com.erp.controller.role.request.RoleFindRequest;
import com.erp.controller.role.response.RoleFindAllResponse;
import com.erp.controller.role.response.RoleFindResponse;
import com.erp.controller.user.request.UserFindRequest;
import com.erp.controller.user.response.UserFindAllResponse;
import com.erp.usecase.role.RoleCreateUseCase;
import com.erp.usecase.role.RoleDeleteUseCase;
import com.erp.usecase.role.RoleEditUseCase;
import com.erp.usecase.role.RoleFindUseCase;
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

@RequestMapping("/role")
@Tag(name = "角色")
@RestController
@RequiredArgsConstructor
@Validated
public class RoleController {

    private final RoleCreateUseCase roleCreateUseCase;

    private final RoleEditUseCase roleEditUseCase;

    private final RoleDeleteUseCase roleDeleteUseCase;

    private final RoleFindUseCase roleFindUseCase;

    @Permission("ROLE:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull RoleCreateRequest request) {
        roleCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("ROLE:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull RoleEditRequest request) {
        roleEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("ROLE:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        roleDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("ROLE:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = RoleFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        RoleFindResponse response = roleFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("ROLE:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = RoleFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated RoleFindRequest request) {
        List<RoleFindAllResponse> responses = roleFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

}

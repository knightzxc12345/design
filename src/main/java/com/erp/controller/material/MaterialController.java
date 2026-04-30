package com.erp.controller.material;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.PageResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.material.request.MaterialCreateRequest;
import com.erp.controller.material.request.MaterialEditRequest;
import com.erp.controller.material.request.MaterialFindRequest;
import com.erp.controller.material.request.MaterialPageRequest;
import com.erp.controller.material.response.MaterialFindAllResponse;
import com.erp.controller.material.response.MaterialFindResponse;
import com.erp.usecase.material.MaterialCreateUseCase;
import com.erp.usecase.material.MaterialDeleteUseCase;
import com.erp.usecase.material.MaterialEditUseCase;
import com.erp.usecase.material.MaterialFindUseCase;
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

@RequestMapping("/material")
@Tag(name = "材料")
@RestController
@RequiredArgsConstructor
@Validated
public class MaterialController {

    private final MaterialCreateUseCase materialCreateUseCase;

    private final MaterialEditUseCase materialEditUseCase;

    private final MaterialDeleteUseCase materialDeleteUseCase;

    private final MaterialFindUseCase materialFindUseCase;

    @Permission("MATERIAL:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull MaterialCreateRequest request) {
        materialCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("MATERIAL:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull MaterialEditRequest request) {
        materialEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("MATERIAL:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        materialDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("MATERIAL:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = MaterialFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        MaterialFindResponse response = materialFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("MATERIAL:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = MaterialFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated MaterialFindRequest request) {
        List<MaterialFindAllResponse> responses = materialFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Permission("MATERIAL:READ")
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
            @Validated MaterialPageRequest request) {
        PageResponse<MaterialFindAllResponse> response = materialFindUseCase.findPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}

package com.erp.controller.action;

import com.erp.base.response.CustomResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.action.request.ActionCreateRequest;
import com.erp.controller.action.request.ActionEditRequest;
import com.erp.controller.action.request.ActionFindRequest;
import com.erp.controller.action.response.ActionFindAllResponse;
import com.erp.controller.action.response.ActionFindResponse;
import com.erp.usecase.action.ActionCreateUseCase;
import com.erp.usecase.action.ActionDeleteUseCase;
import com.erp.usecase.action.ActionEditUseCase;
import com.erp.usecase.action.ActionFindUseCase;
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

@RequestMapping("/action")
@Tag(name = "動作")
@RestController
@RequiredArgsConstructor
@Validated
public class ActionController {

    private final ActionCreateUseCase actionCreateUseCase;

    private final ActionEditUseCase actionEditUseCase;

    private final ActionDeleteUseCase actionDeleteUseCase;

    private final ActionFindUseCase actionFindUseCase;

    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull ActionCreateRequest request) {
        actionCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull ActionEditRequest request) {
        actionEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        actionDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = ActionFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        ActionFindResponse response = actionFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ActionFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated ActionFindRequest request) {
        List<ActionFindAllResponse> responses = actionFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

}

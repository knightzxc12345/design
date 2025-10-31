package com.design.controller.quotation;

import com.design.base.api.CompanyType;
import com.design.base.api.CustomResponse;
import com.design.base.api.SystemCode;
import com.design.controller.quotation.request.QuotationCreateRequest;
import com.design.controller.quotation.request.QuotationEditRequest;
import com.design.controller.quotation.request.QuotationFindRequest;
import com.design.controller.quotation.request.QuotationPageRequest;
import com.design.controller.quotation.response.QuotationDownloadResponse;
import com.design.controller.quotation.response.QuotationFindAllResponse;
import com.design.controller.quotation.response.QuotationFindResponse;
import com.design.controller.quotation.response.QuotationPageResponse;
import com.design.entity.enums.QuotationStatus;
import com.design.usecase.quotation.QuotationCreateUseCase;
import com.design.usecase.quotation.QuotationDownloadUseCase;
import com.design.usecase.quotation.QuotationEditUseCase;
import com.design.usecase.quotation.QuotationFindUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequestMapping("/quotation")
@Tag(name = "報價單")
@RestController
@RequiredArgsConstructor
@Validated
public class QuotationController {

    private final QuotationCreateUseCase quotationCreateUseCase;

    private final QuotationEditUseCase quotationEditUseCase;

    private final QuotationFindUseCase quotationFindUseCase;

    private final QuotationDownloadUseCase quotationDownloadUseCase;

    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull QuotationCreateRequest request) {
        quotationCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull String uuid,
            @RequestBody @Validated @NotNull QuotationEditRequest request) {
        quotationEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "變更狀態")
    @PatchMapping(
            value = "v1/{uuid}/{status}"
    )
    public CustomResponse updateStatus(
            @PathVariable("uuid") @NotNull String uuid,
            @PathVariable("status") @NotNull QuotationStatus status) {
        quotationEditUseCase.updateStatus(uuid, status);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = QuotationFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull String uuid) {
        QuotationFindResponse response = quotationFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = QuotationFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated QuotationFindRequest request) {
        List<QuotationFindAllResponse> responses = quotationFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = QuotationPageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @Validated QuotationPageRequest request) {
        QuotationPageResponse response = quotationFindUseCase.findByPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Operation(summary = "下載報價單")
    @GetMapping("v1/download/{company}/{uuid}")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<byte[]> download(
            @PathVariable("company") @NotNull CompanyType companyType,
            @PathVariable("uuid") @NotNull String uuid) {
        QuotationDownloadResponse response = quotationDownloadUseCase.download(companyType, uuid);
        return ResponseEntity.ok()
                .contentType(response.mediaType())
                .body(response.file());
    }

}

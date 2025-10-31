package com.design.controller.file;

import com.design.base.api.CustomResponse;
import com.design.base.api.SystemCode;
import com.design.controller.file.request.FileCreateRequest;
import com.design.controller.file.request.FileEditRequest;
import com.design.controller.file.request.FileFindRequest;
import com.design.controller.file.request.FilePageRequest;
import com.design.controller.file.response.FileFindAllResponse;
import com.design.controller.file.response.FilePageResponse;
import com.design.usecase.file.FileCreateUseCase;
import com.design.usecase.file.FileEditUseCase;
import com.design.usecase.file.FileFindUseCase;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/file")
@Tag(name = "檔案")
@RestController
@RequiredArgsConstructor
@Validated
public class FileController {

    private final FileCreateUseCase fileCreateUseCase;

    private final FileEditUseCase fileEditUseCase;

    private final FileFindUseCase fileFindUseCase;

    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull FileCreateRequest request,
            @RequestPart(name = "file", required = false) List<MultipartFile> files) {
        fileCreateUseCase.create(request, files);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = FilePageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @Validated FilePageRequest request) {
        FilePageResponse response = fileFindUseCase.findByPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}

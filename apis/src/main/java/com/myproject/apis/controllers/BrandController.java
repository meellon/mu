package com.myproject.apis.controllers;

import com.myproject.apis.services.reqs.BrandReq;
import com.myproject.apis.services.BrandService;
import com.myproject.models.domains.dto.BrandDTO;
import com.myproject.models.structures.controllers.IBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Brand-Apis", description = "Brand API")
@RequiredArgsConstructor
@RequestMapping("/brands")
@RestController
public class BrandController implements IBaseController<BrandDTO, BrandReq> {

    private final BrandService brandService;

    @Override
    @Operation(summary = "브랜드 정보 등록", description = "Brand 정보를 등록 합니다.")
    @PostMapping
    public BrandDTO create(
            @Valid @RequestBody BrandReq brandReq
    ) {
        return brandService.create(brandReq.toDTO());
    }

    @Override
    @Operation(summary = "브랜드 정보 삭제", description = "Brand 정보를 삭제 합니다.")
    @DeleteMapping("/{id}")
    public BrandDTO delete(
            @PathVariable int id
    ) {
        return brandService.delete(id);
    }

    @Override
    @Operation(summary = "브랜드 정보 수정", description = "Brand 정보를 수정 합니다.")
    @PutMapping("/{id}")
    public BrandDTO update(
            @PathVariable int id,
            @Valid @RequestBody BrandReq brandReq
    ) {
        return brandService.update(id, brandReq.toDTO());
    }
}

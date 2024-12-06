package com.myproject.apis.controllers;

import com.myproject.models.domains.dto.CoordinationDTO;
import com.myproject.apis.services.reqs.CoordinationReq;
import com.myproject.apis.services.CoordinationService;
import com.myproject.models.structures.controllers.IBaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coordination-Apis", description = "Coordination API")
@RequiredArgsConstructor
@RequestMapping("/coordinations")
@org.springframework.web.bind.annotation.RestController
public class CoordinationController implements IBaseController<CoordinationDTO, CoordinationReq> {

    private final CoordinationService coordinationService;

    @Override
    @Operation(summary = "코디 정보 등록", description = "Coordination 정보를 등록 합니다.")
    @PostMapping
    public CoordinationDTO create(
            @Valid @RequestBody CoordinationReq coordinationReq
    ) {
        return coordinationService.create(coordinationReq.toDTO());
    }

    @Override
    @Operation(summary = "코디 정보 삭제", description = "Coordination 정보를 삭제 합니다.")
    @DeleteMapping("/{id}")
    public CoordinationDTO delete(
            @PathVariable int id
    ) {
        return coordinationService.delete(id);
    }

    @Override
    @Operation(summary = "코디 정보 수정", description = "Coordination 정보를 수정 합니다.")
    @PutMapping("/{id}")
    public CoordinationDTO update(
            @PathVariable int id,
            @Valid @RequestBody CoordinationReq coordinationReq
    ) {
        return coordinationService.update(id, coordinationReq.toDTO());
    }
}

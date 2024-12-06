package com.myproject.apis.services.reps;

import com.myproject.models.domains.dto.CoordinationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema
public class MinPriceWithEveryCategoriesRep {
    private Integer totalSum;
    private List<CoordinationDTO> categories;
}

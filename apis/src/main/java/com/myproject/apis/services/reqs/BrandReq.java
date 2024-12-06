package com.myproject.apis.services.reqs;

import com.myproject.models.domains.dto.BrandDTO;
import com.myproject.models.domains.dto.CoordinationDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class BrandReq {
    @NotBlank
    private String name;

    public BrandDTO toDTO() {
        return BrandDTO.builder()
                .name(this.name)
                .build();
    }
}

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
public class CoordinationReq {
    @NotBlank
    private String brand;
    @NotBlank
    private String category;
    @NotBlank
    private Integer price;

    public CoordinationDTO toDTO() {
        return CoordinationDTO.builder()
                .brand(BrandDTO.builder()
                        .name(this.brand)
                    .build())
                .category(this.category)
                .price(this.price)
                .build();
    }
}

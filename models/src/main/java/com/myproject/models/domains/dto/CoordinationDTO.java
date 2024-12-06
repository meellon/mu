package com.myproject.models.domains.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myproject.models.domains.entities.CoordinationEntity;
import com.myproject.models.domains.enumerations.CoordinationCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "코디네이션 객체 정보")
public class CoordinationDTO {

    @Builder.Default
    private Integer id = 0;

    @Schema(description = "브랜드")
    private String brandName;
    @JsonIgnore
    private BrandDTO brand;
    @Schema(description = "카테고리 이름", example = "상의")
    private String category;
    @Schema(description = "최소 가격", example = "10000")
    private Integer price;

    public void update(CoordinationDTO toDTO) {
        this.brand = toDTO.getBrand();
        this.category = toDTO.getCategory();
        this.price = toDTO.getPrice();
    }

    public CoordinationEntity toEntity() {
        return CoordinationEntity.builder()
                .id(this.id)
                .brand(this.brand.toEntity())
                .category(CoordinationCategory.of(this.category))
                .price(this.price)
                .build();
    }

    public static CoordinationDTO fromEntity(CoordinationEntity entity) {
        return CoordinationDTO.builder()
                .id(entity.getId())
                .brandName(entity.getBrand().getName())
                .brand(BrandDTO.fromEntity(entity.getBrand()))
                .category(entity.getCategory().getName())
                .price(entity.getPrice())
                .build();
    }
}

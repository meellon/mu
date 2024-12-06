package com.myproject.models.domains.dto;

import com.myproject.models.domains.entities.BrandEntity;
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
@Schema(description = "브랜드 객체 정보")
public class BrandDTO {

    @Builder.Default
    private Integer id = 0;

    @Schema(description = "브랜드 이름", example = "C")
    private String name;

    public void update(BrandDTO toDTO) {
        this.name = toDTO.getName();
    }

    public BrandEntity toEntity() {
        return BrandEntity.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public static BrandDTO fromEntity(BrandEntity entity) {
        return BrandDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}

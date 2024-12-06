package com.myproject.apis.services.reps;

import com.myproject.models.domains.dto.CoordinationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class MinMaxPriceWithBrandByCategoryRep {
    private String category;
    @Builder.Default
    private Map<String, Object> response = new HashMap<>();

    public static class MinMaxPriceWithBrandByCategoryRepBuilder {
        public MinMaxPriceWithBrandByCategoryRep.MinMaxPriceWithBrandByCategoryRepBuilder bind(CoordinationDTO cheapestPrice, CoordinationDTO expensivePrice) {
            this.response$set = Boolean.TRUE;
            this.response$value = Map.of(
                    "카테고리", this.category,
                    "최저가", Map.of(
                            "브랜드", cheapestPrice.getBrand().getName(),
                            "가격", String.format("%,d", cheapestPrice.getPrice())),
                    "최고가", Map.of(
                            "브랜드", expensivePrice.getBrand().getName(),
                            "가격", String.format("%,d", expensivePrice.getPrice()))
            );

            return this;
        }
    }
}

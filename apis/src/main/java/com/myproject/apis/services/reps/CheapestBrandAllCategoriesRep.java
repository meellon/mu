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
public class CheapestBrandAllCategoriesRep {
    @Builder.Default
    private Map<String, Object> response = new HashMap<>();

    public static class CheapestBrandAllCategoriesRepBuilder {
        public CheapestBrandAllCategoriesRepBuilder categories(List<CoordinationDTO> categories) {
            CoordinationDTO first = categories.get(0);

            this.response$set = Boolean.TRUE;
            this.response$value = Map.of("최저가", Map.of(
                    "브랜드", first.getBrand().getName(),
                    "카테고리", categories.stream()
                            .map(row -> Map.of(
                                    "카테고리", row.getCategory(),
                                    "가격", String.format("%,d", row.getPrice())
                            ))
                            .collect(Collectors.toList()),
                    "총액", String.format("%,d", categories.stream().mapToInt(CoordinationDTO::getPrice).sum())
            ));

            return this;
        }
    }
}

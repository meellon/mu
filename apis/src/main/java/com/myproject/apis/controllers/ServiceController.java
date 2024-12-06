package com.myproject.apis.controllers;

import com.myproject.apis.services.reps.MinMaxPriceWithBrandByCategoryRep;
import com.myproject.models.domains.dto.CoordinationDTO;
import com.myproject.apis.services.reps.CheapestBrandAllCategoriesRep;
import com.myproject.apis.services.reps.MinPriceWithEveryCategoriesRep;
import com.myproject.apis.services.CoordinationService;
import com.myproject.models.structures.enumerations.PriceSearchType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Service-Apis", description = "Service API")
@RequiredArgsConstructor
@RequestMapping("/services")
@org.springframework.web.bind.annotation.RestController
public class ServiceController {

    private final CoordinationService coordinationService;

    @Operation(summary = "카테고리별 최저가 브랜드 조회",
            description = "카테고리 별 최저가격 브랜드와 상품가격, 총액을 조회하는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카테고리별 최소 가격 정보",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MinPriceWithEveryCategoriesRep.class)
                            )
                    )
            })
    @GetMapping("/coordinations/mins")
    public ResponseEntity<MinPriceWithEveryCategoriesRep> getMinPriceWithEveryCategories(
    ) {
        List<CoordinationDTO> minPrices = coordinationService.getMinPriceWithEveryCategories();

        return ResponseEntity.ok(MinPriceWithEveryCategoriesRep.builder()
                        .categories(minPrices)
                        .totalSum(minPrices.stream().mapToInt(CoordinationDTO::getPrice).sum())
                        .build());
    }

    @Operation(
            summary = "브랜드 합산 최저 금액 조회 (단일 브랜드 기준)",
            description = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "브랜드 단위 최소 가격 정보",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                    { "최저가":{ "브랜드":"D", "카테고리":[ {"카테고리":"상의","가격":"10,100"}, {"카테고리":"아우터","가격":"5,100"}, {"카테고리":"바지","가격":"3,000"}, {"카테고리":"스니커즈","가격":"9,500"}, {"카테고리":"가방","가격":"2,500"}, {"카테고리":"모자","가격":"1,500"}, {"카테고리":"양말","가격":"2,400"}, {"카테고리":"액세서리","가격":"2,000"} ], "총액":"36,100" } }
                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/coordinations/brands")
    public ResponseEntity<Map<String, Object>> getCheapestBrandAllCategories(
    ) {
        List<CoordinationDTO> minPrices = coordinationService.getCheapestBrandAllCategories();

        return ResponseEntity.ok(CheapestBrandAllCategoriesRep.builder()
                .categories(minPrices)
                .build().getResponse());
    }

    @Operation(
            summary = "카테고리 이름으로 최저,최고가 브랜드와 상품 가격 조회",
            description = "카테고리 이름으로 최저,최고 가격 브랜드와 상품 가격을 조회하는 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카테고리 이름으로 조회",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                    { "카테고리":"상의","최저가" : [ {"브랜드" : "C", "가격" : "10,000"} ], "최고가" : [ {"브랜드" : "I", "가격" : "11,400"} ] }
                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/coordinations/brands/{category}")
    public ResponseEntity<Map<String, Object>> getMinMaxPriceWithBrandByCategory(
            @PathVariable String category
    ) {
        CoordinationDTO cheapestPrice = coordinationService.getMinMaxPriceWithBrandByCategory(category, PriceSearchType.CHEAPEST);
        CoordinationDTO expensivePrice = coordinationService.getMinMaxPriceWithBrandByCategory(category, PriceSearchType.EXPENSE);

        return ResponseEntity.ok(MinMaxPriceWithBrandByCategoryRep.builder()
                .category(category)
                .bind(cheapestPrice, expensivePrice)
            .build().getResponse());
    }
}

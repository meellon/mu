package com.myproject.apis.services;

import com.myproject.models.domains.enumerations.CoordinationCategory;
import com.myproject.models.domains.services.repositories.CoordinationRepositoryService;
import com.myproject.models.domains.dto.CoordinationDTO;
import com.myproject.models.structures.enumerations.PriceSearchType;
import com.myproject.models.structures.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoordinationService extends BaseService<CoordinationDTO> {

    private final CoordinationRepositoryService repositoryService;

    @Override
    public CoordinationDTO create(CoordinationDTO toDTO) {
        return CoordinationDTO.fromEntity(repositoryService.create(toDTO.toEntity()));
    }

    @Override
    public CoordinationDTO update(Integer id, CoordinationDTO toDTO) {
        CoordinationDTO coordination = CoordinationDTO.fromEntity(repositoryService.findById(id));
        coordination.update(toDTO);
        return CoordinationDTO.fromEntity(repositoryService.update(coordination.toEntity()));
    }

    @Override
    public CoordinationDTO delete(Integer id) {
        return CoordinationDTO.fromEntity(repositoryService.deleteById(id));
    }

    public List<CoordinationDTO> getMinPriceWithEveryCategories() {
        return repositoryService.findMinPriceWithEveryCategories().stream()
                .map(CoordinationDTO::fromEntity).
                collect(Collectors.toList());
    }

    public List<CoordinationDTO> getCheapestBrandAllCategories() {
        return repositoryService.findCheapestBrandAllCategories().stream()
                .map(CoordinationDTO::fromEntity).
                collect(Collectors.toList());
    }

    public CoordinationDTO getMinMaxPriceWithBrandByCategory(String category, PriceSearchType cheapest) {
        Sort sort = Sort.by(Sort.Order.asc("id"));
        if(cheapest.equals(PriceSearchType.EXPENSE)) {
            sort = Sort.by(Sort.Order.desc("price"));
        }
        if(cheapest.equals(PriceSearchType.CHEAPEST)) {
            sort = Sort.by(Sort.Order.asc("price"));

        }
        return CoordinationDTO.fromEntity(repositoryService.findMinMaxPriceWithBrandByCategory(CoordinationCategory.of(category), sort));
    }
}

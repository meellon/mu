package com.myproject.apis.services;

import com.myproject.models.domains.dto.BrandDTO;
import com.myproject.models.domains.dto.CoordinationDTO;
import com.myproject.models.domains.enumerations.CoordinationCategory;
import com.myproject.models.domains.services.repositories.BrandRepositoryService;
import com.myproject.models.structures.enumerations.PriceSearchType;
import com.myproject.models.structures.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService extends BaseService<BrandDTO> {

    private final BrandRepositoryService repositoryService;

    @Override
    public BrandDTO create(BrandDTO toDTO) {
        return BrandDTO.fromEntity(repositoryService.create(toDTO.toEntity()));
    }

    @Override
    public BrandDTO update(Integer id, BrandDTO toDTO) {
        BrandDTO brand = BrandDTO.fromEntity(repositoryService.findById(id));
        brand.update(toDTO);
        return BrandDTO.fromEntity(repositoryService.update(brand.toEntity()));
    }

    @Override
    public BrandDTO delete(Integer id) {
        return BrandDTO.fromEntity(repositoryService.deleteById(id));
    }

}

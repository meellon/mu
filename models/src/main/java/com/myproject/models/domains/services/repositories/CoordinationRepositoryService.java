package com.myproject.models.domains.services.repositories;

import com.myproject.models.components.MessageComponent;
import com.myproject.models.domains.entities.CoordinationEntity;
import com.myproject.models.domains.enumerations.CoordinationCategory;
import com.myproject.models.handlers.https.NotFoundException;
import com.myproject.models.structures.repositories.ICreateRepository;
import com.myproject.models.structures.repositories.IDeleteRepository;
import com.myproject.models.structures.repositories.IQueryRepository;
import com.myproject.models.structures.repositories.IUpdateRepository;
import com.myproject.models.structures.services.BaseCRUDService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinationRepositoryService extends BaseCRUDService<CoordinationEntity> {

    private final ICoordinationCustomRepository<CoordinationEntity> iCoordinationCustomRepository;

    public CoordinationRepositoryService(ICreateRepository<CoordinationEntity> iCreateRepository, IQueryRepository<CoordinationEntity> iQueryRepository, IUpdateRepository<CoordinationEntity> iUpdateRepository, IDeleteRepository<CoordinationEntity> iDeleteRepository, ICoordinationCustomRepository<CoordinationEntity> iCoordinationCustomRepository) {
        super(iCreateRepository, iQueryRepository, iUpdateRepository, iDeleteRepository);
        this.iCoordinationCustomRepository = iCoordinationCustomRepository;
    }

    public List<CoordinationEntity> findMinPriceWithEveryCategories() {
        return iCoordinationCustomRepository.findMinPriceWithEveryCategories();
    }

    public List<CoordinationEntity> findCheapestBrandAllCategories() {
        return iCoordinationCustomRepository.findCheapestBrandAllCategories();
    }

    public CoordinationEntity findMinMaxPriceWithBrandByCategory(CoordinationCategory category, Sort sort) {
        return iCoordinationCustomRepository.findMinMaxPriceWithBrandByCategory(category, sort)
                .orElseThrow(() -> new NotFoundException(MessageComponent.MESSAGE.getMessage("service.NOT_FOUND")));
    }
}

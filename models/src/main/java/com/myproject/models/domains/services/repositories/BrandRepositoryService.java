package com.myproject.models.domains.services.repositories;

import com.myproject.models.components.MessageComponent;
import com.myproject.models.domains.entities.BrandEntity;
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
public class BrandRepositoryService extends BaseCRUDService<BrandEntity> {

    public BrandRepositoryService(ICreateRepository<BrandEntity> iCreateRepository, IQueryRepository<BrandEntity> iQueryRepository, IUpdateRepository<BrandEntity> iUpdateRepository, IDeleteRepository<BrandEntity> iDeleteRepository) {
        super(iCreateRepository, iQueryRepository, iUpdateRepository, iDeleteRepository);
    }
}

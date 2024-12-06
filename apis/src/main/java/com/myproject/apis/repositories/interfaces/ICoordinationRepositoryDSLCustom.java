package com.myproject.apis.repositories.interfaces;

import com.myproject.models.domains.entities.CoordinationEntity;
import com.myproject.models.domains.services.repositories.ICoordinationCustomRepository;
import com.myproject.models.structures.repositories.*;

public interface ICoordinationRepositoryDSLCustom extends ICreateRepository<CoordinationEntity>, IUpdateRepository<CoordinationEntity>, IQueryRepository<CoordinationEntity>, IDeleteRepository<CoordinationEntity>, ICoordinationCustomRepository<CoordinationEntity> {
}

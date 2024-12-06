package com.myproject.repositories.domains.interfaces;

import com.myproject.models.domains.entities.BrandEntity;
import com.myproject.models.structures.repositories.ICreateRepository;
import com.myproject.models.structures.repositories.IDeleteRepository;
import com.myproject.models.structures.repositories.IQueryRepository;
import com.myproject.models.structures.repositories.IUpdateRepository;

public interface IBrandRepositoryDSLCustom extends ICreateRepository<BrandEntity>, IUpdateRepository<BrandEntity>, IQueryRepository<BrandEntity>, IDeleteRepository<BrandEntity> {
}

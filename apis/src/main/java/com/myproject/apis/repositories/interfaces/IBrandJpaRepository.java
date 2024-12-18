package com.myproject.apis.repositories.interfaces;

import com.myproject.models.domains.entities.BrandEntity;
import com.myproject.repositories.commons.IJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandJpaRepository extends IJpaRepository<BrandEntity> {
}

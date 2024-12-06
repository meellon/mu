package com.myproject.repositories.domains.interfaces;

import com.myproject.models.domains.entities.CoordinationEntity;
import com.myproject.repositories.commons.IJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoordinationJpaRepository extends IJpaRepository<CoordinationEntity> {
}

package com.myproject.models.structures.repositories;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IQueryRepository<T> {

    Page<T> findByAllQueryDSL();

    Optional<T> findById(int id);

    long count();
}

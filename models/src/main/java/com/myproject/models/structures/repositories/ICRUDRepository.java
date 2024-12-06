package com.myproject.models.structures.repositories;

import org.springframework.data.domain.Page;

public interface ICRUDRepository<T> {

    long count();

    Page<T> findByAll();

    T findById(int id);

    T create(T entity);

    T update(T entity);

    T deleteById(int id);
}

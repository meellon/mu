package com.myproject.models.structures.repositories;

public interface ICreateRepository<T> {
    T save(T entity);
}

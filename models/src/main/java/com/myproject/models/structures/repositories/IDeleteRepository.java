package com.myproject.models.structures.repositories;

public interface IDeleteRepository<T> {
    void deleteById(int id);
}

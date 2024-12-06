package com.myproject.models.structures.services;

public abstract class BaseService<T> {
//    public abstract T get(Integer id);
    public abstract T create(T toDTO);
    public abstract T update(Integer id, T toDTO);
    public abstract T delete(Integer id);
}

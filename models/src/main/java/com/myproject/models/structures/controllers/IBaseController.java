package com.myproject.models.structures.controllers;

public interface IBaseController<T, S> {
//    T get(int id);

    T create(S s);

    T delete(int id);

    T update(int id, S s);
}

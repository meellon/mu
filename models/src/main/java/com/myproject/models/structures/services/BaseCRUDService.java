package com.myproject.models.structures.services;

import com.myproject.models.components.MessageComponent;
import com.myproject.models.structures.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public abstract class BaseCRUDService<T> implements ICRUDRepository<T> {

    private final ICreateRepository<T> iCreateRepository;
    private final IQueryRepository<T> iQueryRepository;
    private final IUpdateRepository<T> iUpdateRepository;
    private final IDeleteRepository<T> iDeleteRepository;

    @Override
    public long count() {
        return iQueryRepository.count();
    }

    @Override
    public Page<T> findByAll() {
        return iQueryRepository.findByAllQueryDSL();
    }

    @Override
    public T findById(int id) {
        return iQueryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(MessageComponent.MESSAGE.getMessage("exception.identity.found")));
    }

    @Override
    public T create(T entity) {
        return iCreateRepository.save(entity);
    }

    @Override
    public T update(T entity) {
        return iUpdateRepository.save(entity);
    }

    @Override
    public T deleteById(int id) {
        T obj = this.findById(id);
        iDeleteRepository.deleteById(id);
        return obj;
    }
}

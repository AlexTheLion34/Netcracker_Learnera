package com.netcracker.learnera.service;

import com.netcracker.learnera.entity.IdentifiableEntity;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;

public interface CrudService<T extends IdentifiableEntity<TId>, TId> {

    T create(T item) throws EntityAlreadyExistsException;

    Iterable<T> getAll();

    T getById(TId id) throws EntityNotFoundException;

    T update(T item) throws EntityNotFoundException;

    void delete(TId id) throws EntityNotFoundException;

}

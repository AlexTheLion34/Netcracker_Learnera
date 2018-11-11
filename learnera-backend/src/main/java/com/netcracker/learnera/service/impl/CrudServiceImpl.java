package com.netcracker.learnera.service.impl;


import com.netcracker.learnera.entity.IdentifiableEntity;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;
import com.netcracker.learnera.service.CrudService;
import org.springframework.data.repository.CrudRepository;

public abstract class CrudServiceImpl<T extends IdentifiableEntity<TId>, TId> implements CrudService<T, TId> {

    private CrudRepository<T, TId> crudRepository;

    public CrudServiceImpl(CrudRepository<T, TId> crudRepository) {
        this.crudRepository = crudRepository;
    }

    protected void forwardReferences(T item) {

    }

    @Override
    public T create(T item) throws EntityAlreadyExistsException {
        forwardReferences(item);
        return crudRepository.save(item);
    }

    @Override
    public Iterable<T> getAll() {
        return crudRepository.findAll();
    }

    @Override
    public T getById(TId id) throws EntityNotFoundException {
        return crudRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public T update(T item) throws EntityNotFoundException {
        if (!crudRepository.existsById(item.getId()))
            throw new EntityNotFoundException("Entity not found!");
        forwardReferences(item);
        return crudRepository.save(item);
    }

    @Override
    public void delete(TId id) throws EntityNotFoundException {
        if (!crudRepository.existsById(id))
            throw new EntityNotFoundException("Entity not found!");
        crudRepository.deleteById(id);
    }
}

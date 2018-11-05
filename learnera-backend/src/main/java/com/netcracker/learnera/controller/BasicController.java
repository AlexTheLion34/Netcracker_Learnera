package com.netcracker.learnera.controller;

import com.netcracker.learnera.entity.IdentifiableEntity;
import com.netcracker.learnera.exception.EntityAlreadyExistsException;
import com.netcracker.learnera.exception.EntityNotFoundException;
import com.netcracker.learnera.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BasicController<T extends IdentifiableEntity<TId>, TId> {

    private CrudService<T, TId> crudService;

    public BasicController(CrudService<T, TId> crudService) {
        this.crudService = crudService;
    }

    @GetMapping
    public ResponseEntity<Iterable<T>> getAll() {
        return new ResponseEntity<>(crudService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable("id") TId id) throws EntityNotFoundException {
        return new ResponseEntity<>(crudService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable("id") TId id, @RequestBody T item)
            throws EntityNotFoundException {
        item.setId(id);
        return new ResponseEntity<>(crudService.update(item), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T item) throws EntityAlreadyExistsException {
        return new ResponseEntity<>(crudService.create(item), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") TId id) throws EntityNotFoundException {
        crudService.delete(id);
        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }

}

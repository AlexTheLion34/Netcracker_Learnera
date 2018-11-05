package com.netcracker.learnera.entity;

public interface IdentifiableEntity<TId> {
    TId getId();
    void setId(TId id);
}

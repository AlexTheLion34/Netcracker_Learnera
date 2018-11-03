package com.netcracker.learnera.exception;

public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException() {
        super();
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}

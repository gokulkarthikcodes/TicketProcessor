package com.sync.ticketprocessor.exception;

public class RecordAlreadyExistsException extends RuntimeException{

    public RecordAlreadyExistsException(String exception) {
        super(exception);
    }
}

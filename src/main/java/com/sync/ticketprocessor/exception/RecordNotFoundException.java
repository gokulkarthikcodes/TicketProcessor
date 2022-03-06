package com.sync.ticketprocessor.exception;

public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String exception) {
        super(exception);
    }
}

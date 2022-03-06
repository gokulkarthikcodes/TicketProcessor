package com.sync.ticketprocessor.exception;

public class InputValidationFailedException extends RuntimeException {

    public InputValidationFailedException(String exception) {
        super(exception);
    }
}

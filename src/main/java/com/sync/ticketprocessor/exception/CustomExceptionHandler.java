package com.sync.ticketprocessor.exception;

import com.sync.ticketprocessor.constants.ConstantsUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InputValidationFailedException.class)
    public final ResponseEntity<Object> handleInputValidationFailedException(InputValidationFailedException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(ConstantsUtil.INVALID_ARGUMENT_IN_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(ConstantsUtil.RECORD_NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public final ResponseEntity<Object> handleRecordAlreadyExistsException(RecordAlreadyExistsException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ErrorResponse error = new ErrorResponse(ConstantsUtil.RECORD_ALREADY_EXISTS, details);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}

package com.dengue_webapp.dengue_webapp.exceptions;

import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        StandardResponse response = new StandardResponse(400, ex.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<StandardResponse>handleNoDataFoundException(NoDataFoundException ex) {
        StandardResponse response = new StandardResponse(404, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidArgumentExeception.class)
    public ResponseEntity<StandardResponse> handleInvalidArgumentError(InvalidArgumentExeception ex) {
        StandardResponse response = new StandardResponse(400, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleGenericError(Exception ex) {
        StandardResponse response = new StandardResponse(500, "Unexpected error occurred", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

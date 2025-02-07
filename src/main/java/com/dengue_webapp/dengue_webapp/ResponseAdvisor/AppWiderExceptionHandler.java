package com.dengue_webapp.dengue_webapp.ResponseAdvisor;

import com.dengue_webapp.dengue_webapp.dto.response.ResponsePHIDto;
import com.dengue_webapp.dengue_webapp.exceptions.EntryNotFoundException;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWiderExceptionHandler {

    public AppWiderExceptionHandler(){

    }

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponse> handleEntryNotFoundException(EntryNotFoundException e){
        return new ResponseEntity<>(
                new StandardResponse(4040, e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

}

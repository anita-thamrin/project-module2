package com.example.travelplanner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ContollerAdvice
public class GlobalExceptionHandler {
       @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<String> handleTripNotFoundException(TripNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    } 
}

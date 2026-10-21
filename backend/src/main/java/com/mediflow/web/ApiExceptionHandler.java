package com.mediflow.web;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class) public ResponseEntity<?> missing(NoSuchElementException e){return ResponseEntity.status(404).body(Collections.singletonMap("message",e.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<?> invalid(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Collections.singletonMap("message",e.getBindingResult().getFieldErrors().get(0).getField()+" is invalid"));}
    @ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<?> duplicate(){return ResponseEntity.status(409).body(Collections.singletonMap("message","A patient with this phone number already exists"));}
    @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) public ResponseEntity<?> business(RuntimeException e){return ResponseEntity.badRequest().body(Collections.singletonMap("message",e.getMessage()));}
}

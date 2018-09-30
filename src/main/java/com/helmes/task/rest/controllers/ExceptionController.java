package com.helmes.task.rest.controllers;

import com.helmes.task.exception.AppException;
import com.helmes.task.exception.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppException> handleAppException(AppException e) {
        return ResponseEntity.badRequest().body(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppException> handleRequesrValidationException(MethodArgumentNotValidException e) {
        List<com.helmes.task.exception.Error> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors()
                .forEach(err -> errors.add(
                        new com.helmes.task.exception.Error(
                                "field.invalid." + err.getField(),
                                err.getDefaultMessage()
                        )
                        )
                );

        return ResponseEntity.badRequest().body(new AppException(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppException> handleException(Exception e) {
        log.error("UNKNOWN_ERROR", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AppException(Errors.UNKNOWN_EXCEPTION.getKeyword(), e.getMessage()));
    }

}
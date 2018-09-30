package com.helmes.task.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"cause", "stackTrace","localizedMessage","message","suppressed"})
public class AppException extends Exception {

    private List<Error> errors;

    public AppException(String message, Throwable cause, String keyword, String description) {
        super(message, cause);
        this.errors = Collections.singletonList(new Error(keyword, description));

    }

    public AppException(String keyword, String description) {
        super();
        this.errors = Collections.singletonList(new Error(keyword, description));

    }

    public AppException(Errors error) {
        super();
        this.errors = Collections.singletonList(new Error(error.getKeyword(), error.getDescription()));
    }

    public AppException(List<Error> errors) {
        super();
        this.errors = errors;
    }
}

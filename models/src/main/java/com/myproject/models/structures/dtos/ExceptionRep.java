package com.myproject.models.structures.dtos;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
public class ExceptionRep implements Serializable {
    private HttpStatus status;
    private String message;
    private String exception;

    public ExceptionRep(HttpStatus status, String message, String exception) {
        this.status = status;
        this.message = message;
        this.exception = exception;
    }
}

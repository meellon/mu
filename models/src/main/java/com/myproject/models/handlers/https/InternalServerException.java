package com.myproject.models.handlers.https;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String msg) {
        super(msg);
    }
}

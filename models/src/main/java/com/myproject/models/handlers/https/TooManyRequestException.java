package com.myproject.models.handlers.https;

public class TooManyRequestException extends RuntimeException {

    public TooManyRequestException(String msg) {
        super(msg);
    }
}

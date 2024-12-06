package com.myproject.models.handlers.https;

public class RequestTimeOutException extends RuntimeException {

    public RequestTimeOutException(String msg) {
        super(msg);
    }
}

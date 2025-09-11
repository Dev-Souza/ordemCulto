package com.mava.ordemCulto.exceptions;

public class DateIsBeforeException extends RuntimeException {
    public DateIsBeforeException(String message) {
        super(message);
    }
}

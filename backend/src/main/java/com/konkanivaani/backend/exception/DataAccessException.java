package com.konkanivaani.backend.exception;

/** Wraps checked exceptions from the database so the rest of the app stays clean. */
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);   // 'cause' keeps the original stack trace
    }
}
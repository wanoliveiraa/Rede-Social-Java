package com.example.sysmap.parrot.Application.Exception;


public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message,cause);
    }
}

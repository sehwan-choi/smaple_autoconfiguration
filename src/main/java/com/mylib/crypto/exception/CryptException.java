package com.mylib.crypto.exception;

public class CryptException extends RuntimeException {

    public CryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptException(String message) {
        super(message);
    }

}

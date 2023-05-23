package com.pinheng.youcai;

public class OpenException extends Exception{
    public OpenException() {
        super();
    }

    public OpenException(String message) {
        super(message);
    }

    public OpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenException(Throwable cause) {
        super(cause);
    }
}

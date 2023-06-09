package com.netease.youcai;

public class OpenException extends RuntimeException {

    private final int code;

    public OpenException(int code, String message) {
        super(message);
        this.code = code;
    }

    public OpenException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @SuppressWarnings("unused")
    public int getCode() {
        return code;
    }
}

package com.helpmycity;

public enum StatusCode {
    SUCCESS(200),
    FAILED(400);

    private int code;

    StatusCode(int i) {
        this.code = i;
    }


    public int getCode() {
        return code;
    }
}

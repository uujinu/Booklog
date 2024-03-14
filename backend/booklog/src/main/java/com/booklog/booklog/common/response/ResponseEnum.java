package com.booklog.booklog.common.response;

public enum ResponseEnum {
    SUCCESS("SUCCESS"),
    FAIL("FAIL");

    private final String result;

    ResponseEnum(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return this.result;
    }
}
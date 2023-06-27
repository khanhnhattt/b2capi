package com.example.b2capi.domain.enums;

public enum OrderStatus {
    UNCONFIRMED("NOT CONFIRMED YET"),
    IN_PROCESS("PROCESSING"),
    DELIVERY("CURRENTLY ON DELIVERY"),
    DONE("DONE"),
    CANCELLED("CANCELLED");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.example.b2capi.domain.enums;

public enum OrderStatus {
    ORDER("ORDER"),
    CART("CART");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.example.b2capi.domain.enums;

public enum PaymentStatus {
    PAID("PAID"),
    UNPAID("UNPAID");

    private String value;

    public String getValue() {
        return value;
    }

    PaymentStatus(String value) {
        this.value = value;
    }
}

package com.example.b2capi.domain.enums;

public enum PaymentMethod {
    COD("COD"),
    VISA("VISA"),
    INTERNET_BANKING("IB");

    private String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

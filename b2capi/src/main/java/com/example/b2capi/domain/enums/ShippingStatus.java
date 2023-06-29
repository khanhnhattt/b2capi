package com.example.b2capi.domain.enums;

public enum ShippingStatus {
    PROCESSING("PROCESSING IN STORE"),
    DELIVERING("ON DELIVERY TO BUYER"),
    DELIVERED("DELIVERY FINISHED"),
    CANCELLED("CANCELLED");

    private String value;

    public String getValue() {
        return value;
    }

    ShippingStatus(String value) {
        this.value = value;
    }
}

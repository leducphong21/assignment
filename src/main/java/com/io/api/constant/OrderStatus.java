package com.io.api.constant;

public enum OrderStatus {
    PENDING(0),
    PROCESSING(1),
    SHIPPED(2),
    DELIVERED(3),
    CANCELLED(4);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderStatus fromValue(int value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status value: " + value);
    }
}

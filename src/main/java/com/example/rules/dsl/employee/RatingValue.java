package com.example.rules.dsl.employee;

public enum RatingValue {
    LOW_PERFORMANCE(1),
    NOT_MET_EXPECTATIONS(2),
    MET_EXPECTATIONS(3),
    ABOVE_EXPECTATIONS(4),
    EXTRAORDINARY(5);

    private final int ratingValue;

    RatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public int getRatingValue() {
        return ratingValue;
    }
}

package com.example.rules.dsl.employee;

import java.time.LocalDateTime;

public class PerformanceRating {
    private final RatingValue ratingValue;
    private final LocalDateTime ratingDate;

    public PerformanceRating(RatingValue ratingValue, LocalDateTime ratingDate) {
        this.ratingValue = ratingValue;
        this.ratingDate = ratingDate;
    }

    public RatingValue getRatingValue() {
        return ratingValue;
    }

    public LocalDateTime getRatingDate() {
        return ratingDate;
    }
}

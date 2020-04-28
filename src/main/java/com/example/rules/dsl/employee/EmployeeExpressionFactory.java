package com.example.rules.dsl.employee;

import com.example.rules.dsl.Expression;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public final class EmployeeExpressionFactory {
    private EmployeeExpressionFactory() {
    }

    public static Expression<Employee> employeeEligibleForGratuity() {
        return new Expression<>(e -> e.getDoj().isBefore(LocalDateTime.now().minusYears(5)));
    }

    public static Expression<Employee> employeeSpentOneYearInCurrentRole() {
        return new Expression<>(e -> e.getRole() != null
                && e.getRole().getRoleDate().isBefore(now().minusYears(1)));
    }

    public static Expression<Employee> employeeWithRatingEligibleForInternalJobSwitch() {
        return new Expression<>(e -> e.getPerformanceRating() != null
                && RatingValue.ABOVE_EXPECTATIONS.compareTo(e.getPerformanceRating().getRatingValue()) <= 0);
    }

    public static Expression<Employee> employeeWithBadRatingInLastThreeMonthsOrLess() {
        return new Expression<>(e -> e.getPerformanceRating() != null
                && RatingValue.LOW_PERFORMANCE.equals(e.getPerformanceRating().getRatingValue()));
    }

    public static Expression<Employee> newEmployee() {
        return new Expression<>(e -> e.getDoj().isAfter(now().minusMonths(6)));
    }
}

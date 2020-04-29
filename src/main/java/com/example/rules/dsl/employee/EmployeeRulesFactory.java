package com.example.rules.dsl.employee;

import com.example.rules.dsl.expression.Expression;
import com.example.rules.dsl.expression.ExpressionBuilder;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import static java.time.LocalDateTime.now;

public final class EmployeeRulesFactory {
    private EmployeeRulesFactory() {
    }

    public static Expression<Employee> employeeEligibleForGratuity() {
        return new ExpressionBuilder<Employee>()
                .withPredicate(e -> e.getDoj().isBefore(LocalDateTime.now().minusYears(5)))
                .build();
    }

    private static Predicate<Employee> employeeSpentOneYearInCurrentRole() {
        return e -> e.getRole() != null
                && e.getRole().getRoleDate().isBefore(now().minusYears(1));
    }

    private static Predicate<Employee> employeeWithRatingAboveExpectationsOrHigher() {
        return e -> e.getPerformanceRating() != null
                && RatingValue.ABOVE_EXPECTATIONS.compareTo(e.getPerformanceRating().getRatingValue()) <= 0;
    }

    private static Predicate<Employee> employeeWithBadRatingInLastThreeMonthsOrLess() {
        return e -> e.getPerformanceRating() != null
                && RatingValue.LOW_PERFORMANCE.equals(e.getPerformanceRating().getRatingValue())
                && e.getPerformanceRating().getRatingDate().isAfter(now().minusMonths(3));
    }

    private static Predicate<Employee> newEmployee() {
        return e -> e.getDoj().isAfter(now().minusMonths(6));
    }

    public static Expression<Employee> employeeEligibleForInternalJobSwitch() {
        return new ExpressionBuilder<Employee>()
                .not(newEmployee())
                .andnot(employeeWithBadRatingInLastThreeMonthsOrLess())
                .and(employeeWithRatingAboveExpectationsOrHigher())
                .and(employeeSpentOneYearInCurrentRole())
                .build();
    }

}

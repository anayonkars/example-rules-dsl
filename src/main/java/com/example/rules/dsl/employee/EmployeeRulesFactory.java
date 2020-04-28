package com.example.rules.dsl.employee;

import com.example.rules.dsl.expression.Expression;
import com.example.rules.dsl.expression.ExpressionBuilder;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import static java.time.LocalDateTime.now;

public final class EmployeeRulesFactory {
    private EmployeeRulesFactory() {
    }

    public static Predicate<Employee> employeeEligibleForGratuity() {
        return e -> e.getDoj().isBefore(LocalDateTime.now().minusYears(5));
    }

    private static Predicate<Employee> employeeSpentOneYearInCurrentRole() {
        return e -> e.getRole() != null
                && e.getRole().getRoleDate().isBefore(now().minusYears(1));
    }

    private static Predicate<Employee> employeeWithRatingEligibleForInternalJobSwitch() {
        return e -> e.getPerformanceRating() != null
                && RatingValue.ABOVE_EXPECTATIONS.compareTo(e.getPerformanceRating().getRatingValue()) <= 0;
    }

    private static Predicate<Employee> employeeWithBadRatingInLastThreeMonthsOrLess() {
        return e -> e.getPerformanceRating() != null
                && RatingValue.LOW_PERFORMANCE.equals(e.getPerformanceRating().getRatingValue());
    }

    public static Predicate<Employee> newEmployee() {
        return e -> e.getDoj().isAfter(now().minusMonths(6));
    }

    public static Expression<Employee> employeeEligibleForInternalJobSwitch() {
        return new ExpressionBuilder<Employee>()
                .not(newEmployee())
                .andnot(employeeWithBadRatingInLastThreeMonthsOrLess())
                .and(employeeWithRatingEligibleForInternalJobSwitch())
                .and(employeeSpentOneYearInCurrentRole())
                .build();
    }
    /*newEmployee().not()
                        .and(employeeWithBadRatingInLastThreeMonthsOrLess().not())
                        .and(employeeWithRatingEligibleForInternalJobSwitch())
                        .and(employeeSpentOneYearInCurrentRole()*/

}

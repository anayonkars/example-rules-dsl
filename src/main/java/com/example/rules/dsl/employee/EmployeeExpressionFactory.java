package com.example.rules.dsl.employee;

import com.example.rules.dsl.Expression;

import java.time.LocalDateTime;

public final class EmployeeExpressionFactory {
    private EmployeeExpressionFactory() {
    }

    public static Expression<Employee> employeeEligibleForGratuity() {
        return new Expression<>(e -> e.getDoj().isBefore(LocalDateTime.now().minusYears(5)));
    }
}

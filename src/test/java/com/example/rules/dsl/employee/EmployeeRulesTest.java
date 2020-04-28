package com.example.rules.dsl.employee;

import com.example.rules.dsl.Expression;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.example.rules.dsl.employee.EmployeeBuilder.anEmployee;
import static com.example.rules.dsl.employee.EmployeeExpressionFactory.employeeEligibleForGratuity;
import static java.time.LocalDateTime.now;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeRulesTest {
    @Test
    public void shouldEvaluateEmployeeForGratuity() {
        Expression<Employee> employeeEligibleForGratuity = employeeEligibleForGratuity();
        assertFalse(employeeEligibleForGratuity.execute(anEmployee()
                                                        .withId("123")
                                                        .withDoj(now().minusYears(4))
                                                        .build()));
        assertTrue(employeeEligibleForGratuity.execute(anEmployee()
                                                        .withId("123")
                                                        .withDoj(now().minusYears(6))
                                                        .build()));
    }

    @Test
    public void shouldEvaluateEmployeeForInternalJobSwitch() {
        Expression<Employee> newEmployeeExpression = newEmployee();
        Expression<Employee> employeeWithBadRatingInLastThreeMonthsOrLess = employeeWithBadRatingInLastThreeMonthsOrLess();
        Expression<Employee> employeeOnProbationExpression = newEmployeeExpression.or(employeeWithBadRatingInLastThreeMonthsOrLess);
        Expression<Employee> employeeWithRatingEligibleForInternalJobSwitch = employeeWithRatingEligibleForInternalJobSwitch();
        Expression<Employee> employeeSpentOneYearInCurrentRole = employeeSpentOneYearInCurrentRole();
        Expression<Employee> employeeEligibleForInternalJobSwitch = employeeOnProbationExpression.not()
                .and(employeeWithRatingEligibleForInternalJobSwitch)
                .and(employeeSpentOneYearInCurrentRole);
        Employee newEmployee = anEmployee().withDoj(now()).build();
        assertFalse(employeeEligibleForInternalJobSwitch.execute(newEmployee));
        Employee eligible = anEmployee()
                .withDoj(now().minusYears(2))
                .withPerformanceRating(new PerformanceRating(RatingValue.EXTRAORDINARY, now().minusYears(1)))
                .withRole(new Role(RoleValue.ENGG, now().minusYears(1).minusDays(1)))
                .build();
        assertTrue(employeeEligibleForInternalJobSwitch.execute(eligible));
    }

    private Expression<Employee> employeeSpentOneYearInCurrentRole() {
        return new Expression<>(e -> e.getRole() != null
                && e.getRole().getRoleDate().isBefore(now().minusYears(1)));
    }

    private Expression<Employee> employeeWithRatingEligibleForInternalJobSwitch() {
        return new Expression<>(e -> e.getPerformanceRating() != null
                && RatingValue.ABOVE_EXPECTATIONS.compareTo(e.getPerformanceRating().getRatingValue()) <= 0);
    }

    private Expression<Employee> employeeWithBadRatingInLastThreeMonthsOrLess() {
        return new Expression<>(e -> e.getPerformanceRating() != null
                && RatingValue.LOW_PERFORMANCE.equals(e.getPerformanceRating().getRatingValue()));
    }

    private Expression<Employee> newEmployee() {
        return new Expression<>(e -> e.getDoj().isAfter(now().minusMonths(6)));
    }
}

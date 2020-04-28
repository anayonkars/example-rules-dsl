package com.example.rules.dsl.employee;

import com.example.rules.dsl.Expression;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeRulesTest {
    @Test
    public void shouldEvaluateEmployeeForGratuity() {
        Expression<Employee> employeeEligibleForGratuity = new Expression<>(e -> e.getDoj().isBefore(LocalDateTime.now().minusYears(5)));
        assertFalse(employeeEligibleForGratuity.execute(new Employee("123", LocalDateTime.now().minusYears(4))));
        assertTrue(employeeEligibleForGratuity.execute(new Employee("123", LocalDateTime.now().minusYears(6))));
    }
}

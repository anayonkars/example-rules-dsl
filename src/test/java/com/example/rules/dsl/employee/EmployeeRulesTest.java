package com.example.rules.dsl.employee;

import com.example.rules.dsl.Expression;
import com.example.rules.dsl.ExpressionBuilder;
import org.junit.Test;

import static com.example.rules.dsl.employee.EmployeeBuilder.anEmployee;
import static com.example.rules.dsl.employee.EmployeeRulesFactory.*;
import static com.example.rules.dsl.employee.EmployeeRulesFactory.employeeEligibleForInternalJobSwitch;
import static com.example.rules.dsl.employee.RatingValue.EXTRAORDINARY;
import static com.example.rules.dsl.employee.RoleValue.ENGG;
import static java.time.LocalDateTime.now;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeRulesTest {
    @Test
    public void shouldEvaluateEmployeeForGratuity() {
        Expression<Employee> employeeEligibleForGratuity = new ExpressionBuilder<Employee>()
                .withPredicate(employeeEligibleForGratuity())
                .build();
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
        assertFalse(employeeEligibleForInternalJobSwitch()
                        .execute(anEmployee()
                                .withDoj(now())
                                .build()));

        assertTrue(employeeEligibleForInternalJobSwitch()
                    .execute(anEmployee()
                                .withDoj(now().minusYears(2))
                                .withPerformanceRating(new PerformanceRating(EXTRAORDINARY, now().minusYears(1)))
                                .withRole(new Role(ENGG, now().minusYears(1).minusDays(1)))
                                .build()));
    }

}

package com.example.rules.dsl.employee;

import com.example.rules.dsl.Expression;
import org.junit.Test;

import static com.example.rules.dsl.employee.EmployeeBuilder.anEmployee;
import static com.example.rules.dsl.employee.EmployeeExpressionFactory.*;
import static com.example.rules.dsl.employee.RatingValue.EXTRAORDINARY;
import static com.example.rules.dsl.employee.RoleValue.ENGG;
import static java.time.LocalDateTime.now;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeRulesTest {
    @Test
    public void shouldEvaluateEmployeeForGratuity() {
        assertFalse(employeeEligibleForGratuity().execute(anEmployee()
                                                        .withId("123")
                                                        .withDoj(now().minusYears(4))
                                                        .build()));
        assertTrue(employeeEligibleForGratuity().execute(anEmployee()
                                                        .withId("123")
                                                        .withDoj(now().minusYears(6))
                                                        .build()));
    }

    @Test
    public void shouldEvaluateEmployeeForInternalJobSwitch() {
        /*assertFalse(newEmployee().not()
                        .and(employeeWithBadRatingInLastThreeMonthsOrLess().not())
                        .and(employeeWithRatingEligibleForInternalJobSwitch())
                        .and(employeeSpentOneYearInCurrentRole())
                    .execute(anEmployee()
                            .withDoj(now())
                            .build()));
        assertTrue(newEmployee().not()
                        .and(employeeWithBadRatingInLastThreeMonthsOrLess().not())
                        .and(employeeWithRatingEligibleForInternalJobSwitch())
                        .and(employeeSpentOneYearInCurrentRole())
                .execute(anEmployee()
                            .withDoj(now().minusYears(2))
                            .withPerformanceRating(new PerformanceRating(EXTRAORDINARY, now().minusYears(1)))
                            .withRole(new Role(ENGG, now().minusYears(1).minusDays(1)))
                            .build()));*/
    }

}

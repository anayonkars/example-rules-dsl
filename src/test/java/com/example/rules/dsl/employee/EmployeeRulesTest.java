package com.example.rules.dsl.employee;

import com.example.rules.dsl.expression.ExpressionBuilder;
import org.junit.Test;

import static com.example.rules.dsl.employee.EmployeeBuilder.anEmployee;
import static com.example.rules.dsl.employee.EmployeeRulesFactory.*;
import static com.example.rules.dsl.employee.RatingValue.EXTRAORDINARY;
import static com.example.rules.dsl.employee.RoleValue.*;
import static java.time.LocalDateTime.now;
import static org.junit.Assert.*;

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
        assertFalse(employeeEligibleForInternalJobSwitch()
                        .execute(anEmployee()
                                .withDoj(now())
                                .build()));

        assertTrue(employeeEligibleForInternalJobSwitch()
                    .execute(anEmployee()
                                .withDoj(now().minusYears(2))
                                .withPerformanceRating(new PerformanceRating(EXTRAORDINARY,
                                                                            now().minusYears(1)))
                                .withRole(new Role(ENGG,
                                                    now().minusYears(1).minusDays(1)))
                                .build()));
    }

    @Test
    public void shouldEvaluateEmployeeForPromotion() {
        assertFalse(employeeEligibleForPromotion()
                        .execute(anEmployee()
                                    .withDoj(now())
                                    .build()));
    }

    @Test
    public void shouldEvaluateJuniorEmployeeForPromotion() {
        assertTrue(employeeEligibleForPromotion()
                    .execute(anEmployee()
                                .withDoj(now().minusYears(2))
                                .withPerformanceRating(new PerformanceRating(EXTRAORDINARY,
                                        now().minusYears(1)))
                                .withRole(new Role(ENGG,
                                        now().minusYears(1).minusDays(1)))
                                .build()));
    }

    @Test
    public void shouldEvaluateMidLevelEmployeeForPromotion() {
        assertTrue(employeeEligibleForPromotion()
                    .execute(anEmployee()
                                .withDoj(now().minusYears(4))
                                .withPerformanceRating(new PerformanceRating(EXTRAORDINARY, now().minusYears(1)))
                                .withRole(new Role(UNIT_LEAD, now().minusYears(2).minusDays(1)))
                                .build()));
    }

    @Test
    public void shouldEvaluateTopLevelEmployeeForPromotion() {
        assertTrue(employeeEligibleForPromotion()
                    .execute(anEmployee()
                                .withDoj(now().minusYears(8))
                                .withPerformanceRating(new PerformanceRating(EXTRAORDINARY, now().minusYears(1)))
                                .withRole(new Role(AVP, now().minusYears(4).minusDays(1)))
                                .build()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowUnsupportedExpressionWhenInvalidExpressionIsBuilt() {
        new ExpressionBuilder<Employee>()
                .and(e -> e != null)
                .build();
        fail("Expression should not be built if invalid");
    }

}

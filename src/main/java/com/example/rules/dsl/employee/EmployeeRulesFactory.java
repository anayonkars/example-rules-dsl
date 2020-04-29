package com.example.rules.dsl.employee;

import com.example.rules.dsl.expression.Expression;
import com.example.rules.dsl.expression.ExpressionBuilder;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import static com.example.rules.dsl.employee.RatingValue.*;
import static com.example.rules.dsl.employee.RoleValue.REGION_LEAD;
import static com.example.rules.dsl.employee.RoleValue.SENIOR_ENGG;
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
                && ABOVE_EXPECTATIONS.compareTo(e.getPerformanceRating().getRatingValue()) <= 0;
    }

    private static Predicate<Employee> employeeWithBadRatingInLastThreeMonthsOrLess() {
        return e -> e.getPerformanceRating() != null
                && LOW_PERFORMANCE.equals(e.getPerformanceRating().getRatingValue())
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

    public static Expression<Employee> employeeEligibleForPromotion() {
        return new ExpressionBuilder<Employee>()
                .withPredicate(rulesForPromotionOfJuniorEmployee())
                .or(rulesForPromotionOfMidLevelEmployee())
                .or(rulesForPromotionOfTopLevelEmployee())
                .build();
    }

    private static Predicate<Employee> rulesForPromotionOfTopLevelEmployee() {
        return e -> isTopLevelEmployee().test(e)
                    && isEligibleForTopLevelPromotion().test(e);
    }

    private static Predicate<Employee> isEligibleForTopLevelPromotion() {
        return e -> employeeWithAverageRatingInLastNineMonths().negate().test(e)
                    && employeeWithExtraordinaryRating().test(e)
                    && employeeSpentThreeYearsInCurrentRole().test(e);
    }

    private static Predicate<Employee> employeeSpentThreeYearsInCurrentRole() {
        return e -> e.getRole().getRoleDate().isBefore(now().minusYears(3));
    }

    private static Predicate<Employee> employeeWithExtraordinaryRating() {
        return e -> e.getPerformanceRating() != null
                    && e.getPerformanceRating().getRatingValue().equals(EXTRAORDINARY);
    }

    private static Predicate<Employee> employeeWithAverageRatingInLastNineMonths() {
        return e -> e.getPerformanceRating() != null
                    && MET_EXPECTATIONS.compareTo(e.getPerformanceRating().getRatingValue()) >=0;
    }

    private static Predicate<Employee> isTopLevelEmployee() {
        return e -> e.getRole() !=null
                    && e.getRole().getRoleValue().compareTo(REGION_LEAD) >=0;
    }

    private static Predicate<Employee> rulesForPromotionOfMidLevelEmployee() {
        return e -> isMidLevelEmployee().test(e)
                && isEligibleForMidLevelPromotion().test(e);
    }

    private static Predicate<Employee> isMidLevelEmployee() {
        return e -> e.getRole() != null
                    && e.getRole().getRoleValue().compareTo(SENIOR_ENGG) > 0
                    && e.getRole().getRoleValue().compareTo(REGION_LEAD) < 0;
    }

    private static Predicate<Employee> isEligibleForMidLevelPromotion() {
        return e -> employeeNotMetExpectationsInLastSixMonthsOrLess().negate().test(e)
                    && employeeWithRatingAboveExpectationsOrHigher().test(e)
                    && employeeSpentTwoYearsInCurrentRole().test(e);
    }

    private static Predicate<Employee> employeeNotMetExpectationsInLastSixMonthsOrLess() {
        return e -> e.getPerformanceRating() != null
                        && NOT_MET_EXPECTATIONS.compareTo(e.getPerformanceRating().getRatingValue()) >=0
                        && e.getPerformanceRating().getRatingDate().isAfter(now().minusMonths(6));
    }

    private static Predicate<Employee> employeeSpentTwoYearsInCurrentRole() {
        return e -> e.getRole().getRoleDate().isBefore(now().minusYears(2));
    }

    private static Predicate<Employee> rulesForPromotionOfJuniorEmployee() {
        return e -> isJuniorEmployee().test(e)
                    && employeeEligibleForInternalJobSwitch().getPredicate().test(e);
    }

    private static Predicate<Employee> isJuniorEmployee() {
        return e -> e.getRole() == null
                || e.getRole().getRoleValue().compareTo(SENIOR_ENGG) <= 0;
    }
}

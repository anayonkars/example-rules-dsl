package com.example.rules.dsl;

import com.example.rules.dsl.expression.Expression;
import com.example.rules.dsl.expression.ExpressionBuilder;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.*;

public class RulesTest {


    @Test
    public void shouldBeAbleToCreateRule() {
        Expression ex = new Expression();
        assertNotNull(ex);
        assertFalse(ex.execute(new Object()));
    }

    @Test
    public void shouldBeAbleToEvaluateSimpleExpression() {
        Predicate<String> notNullString = s -> s != null;
        Expression<String> ex = new ExpressionBuilder<String>()
                                    .withPredicate(notNullString)
                                    .build();
        boolean result = ex.execute(new String());
        assertTrue(result);
    }

    @Test
    public void shouldBeAbleToEvaluateSimpleOrCondition() {
        Predicate<String> nullString = s -> s == null;
        Expression<String> stringNullExpression = new ExpressionBuilder<String>()
                                                    .withPredicate(nullString)
                                                    .build();
        Predicate<String> emptyString = s -> s != null && s.isEmpty();
        Expression<String> stringEmptyExpression = new ExpressionBuilder<String>()
                                                    .withPredicate(emptyString)
                                                    .build();
        Expression<String> stringNullOrEmptyExpression = new ExpressionBuilder<String>()
                                                            .withPredicate(nullString)
                                                            .or(emptyString)
                                                            .build();
        assertForShouldBeAbleToEvaluateSimpleOrCondition(stringNullExpression, stringEmptyExpression, stringNullOrEmptyExpression);
    }

    private void assertForShouldBeAbleToEvaluateSimpleOrCondition(Expression<String> stringNullExpression,
                                                                  Expression<String> stringEmptyExpression,
                                                                  Expression<String> stringNullOrEmptyExpression) {
        assertTrue(stringNullExpression.execute(null));
        assertFalse(stringNullExpression.execute(new String()));
        assertFalse(stringEmptyExpression.execute(null));
        assertTrue(stringEmptyExpression.execute(new String()));
        assertTrue(stringNullOrEmptyExpression.execute(null));
        assertTrue(stringNullOrEmptyExpression.execute(new String()));
    }

    @Test
    public void shouldBeAbleToEvaluateSimpleAndCondition() {
        Predicate<String> nonNullString = s -> s != null;
        Expression<String> stringNotNullExpression = new Expression<>(nonNullString);
        Predicate<String> nonEmptyString = s -> s != null && !s.isEmpty();
        Expression<String> stringNotEmptyExpression = new Expression<>(nonEmptyString);
        Expression<String> stringNotNullAndNotEmptyExpression = new ExpressionBuilder<String>()
                                                                    .withPredicate(nonNullString)
                                                                    .and(nonEmptyString)
                                                                    .build();
        assertForShouldBeAbleToEvaluateSimpleAndCondition(stringNotNullExpression, stringNotEmptyExpression, stringNotNullAndNotEmptyExpression);
    }

    private void assertForShouldBeAbleToEvaluateSimpleAndCondition(Expression<String> stringNotNullExpression, Expression<String> stringNotEmptyExpression, Expression<String> stringNotNullAndNotEmptyExpression) {
        assertFalse(stringNotNullExpression.execute(null));
        assertTrue(stringNotNullExpression.execute(new String()));
        assertFalse(stringNotEmptyExpression.execute(null));
        assertFalse(stringNotEmptyExpression.execute(new String()));
        assertTrue(stringNotEmptyExpression.execute("abc"));
        assertFalse(stringNotNullAndNotEmptyExpression.execute(null));
        assertFalse(stringNotNullAndNotEmptyExpression.execute(new String()));
        assertTrue(stringNotNullAndNotEmptyExpression.execute("abc"));
    }

    public void shouldBeAbleToEvaluateNegativeCondition() {
        Predicate<String> nullString = s -> s == null;
        Expression<String> stringNullExpression = new Expression<>(nullString);
        Expression stringNotNullExpression = new ExpressionBuilder<String>()
                                                    .not(nullString)
                                                    .build();
        assertTrue(stringNotNullExpression.execute(new String()));
        assertFalse(stringNotNullExpression.execute(null));
    }

    @Test
    public void shouldBeAbleToEvaluateCompositeCondition() {
        Predicate<Integer> zeroInteger = i -> i != null && i == 0;
        Expression<Integer> integerZeroExpression = new Expression<>(zeroInteger);
        Predicate<Integer> negativeInteger = i -> i != null && i < 0;
        Expression<Integer> integerNegativeExpression = new Expression<>(negativeInteger);
        Predicate<Integer> evenInteger = i -> i % 2 == 0;
        Expression<Integer> integerEvenExpression = new Expression<>(evenInteger);
        Expression<Integer> integerPositiveEvenExpression = new ExpressionBuilder<Integer>().
                                                                    not(zeroInteger)
                                                                    .andnot(negativeInteger)
                                                                    .and(evenInteger)
                                                                    .build();
        assertTrue(integerPositiveEvenExpression.execute(6));
        assertFalse(integerPositiveEvenExpression.execute(-6));
        assertFalse(integerPositiveEvenExpression.execute(5));
    }
}

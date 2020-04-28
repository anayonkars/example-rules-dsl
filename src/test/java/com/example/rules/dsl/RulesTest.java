package com.example.rules.dsl;

import org.junit.Test;

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
        Expression<String> ex = new Expression<>(s -> s != null);
        boolean result = ex.execute(new String());
        assertTrue(result);
    }

    @Test
    public void shouldBeAbleToEvaluateSimpleOrCondition() {
        Expression<String> stringNullExpression = new Expression<>(s -> s == null);
        Expression<String> stringEmptyExpression = new Expression<>(s -> s != null && s.isEmpty());
        Expression<String> stringNullOrEmptyExpression = stringNullExpression.or(stringEmptyExpression);
        assertForShouldBeAbleToEvaluateSimpleOrCondition(stringNullExpression, stringEmptyExpression, stringNullOrEmptyExpression);
    }

    private void assertForShouldBeAbleToEvaluateSimpleOrCondition(Expression<String> stringNullExpression, Expression<String> stringEmptyExpression, Expression<String> stringNullOrEmptyExpression) {
        assertTrue(stringNullExpression.execute(null));
        assertFalse(stringNullExpression.execute(new String()));
        assertFalse(stringEmptyExpression.execute(null));
        assertTrue(stringEmptyExpression.execute(new String()));
        assertTrue(stringNullOrEmptyExpression.execute(null));
        assertTrue(stringNullOrEmptyExpression.execute(new String()));
    }

    @Test
    public void shouldBeAbleToEvaluateSimpleAndCondition() {
        Expression<String> stringNotNullExpression = new Expression<>(s -> s != null);
        Expression<String> stringNotEmptyExpression = new Expression<>(s -> s != null && !s.isEmpty());
        Expression<String> stringNotNullAndNotEmptyExpression = stringNotNullExpression.and(stringNotEmptyExpression);
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
}

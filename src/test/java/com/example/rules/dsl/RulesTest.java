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
        Expression<String> stringNullOrEmptyExpression = stringNullExpression.and(stringEmptyExpression);
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
}

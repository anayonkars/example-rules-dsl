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
}

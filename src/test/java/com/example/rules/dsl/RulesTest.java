package com.example.rules.dsl;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RulesTest {
    @Test
    public void shouldBeAbleToCreateRule() {
        Expression ex = new Expression();
        assertNotNull(ex);
    }
}

package com.example.rules.dsl;

import java.util.function.Predicate;

public class Expression<T> {
    private Predicate<T> predicate;

    public Expression(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public Expression() {
        //default no-arg constructor
    }

    public boolean execute(T t) {
        return predicate != null && predicate.test(t);
    }

    public Predicate<T> getPredicate() {
        return predicate;
    }

    public Expression or(Expression<T> expression) {
        return new Expression<T>(this.predicate.or(expression.getPredicate()));
    }

    public Expression<T> and(Expression<T> expression) {
        return new Expression<T>(this.predicate.and(expression.getPredicate()));
    }
}
